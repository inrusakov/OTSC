package com.otsc.backend.services;

import com.otsc.backend.dtos.MessageDto;
import com.otsc.backend.dtos.UserDto;
import com.otsc.backend.entities.Message;
import com.otsc.backend.entities.User;
import com.otsc.backend.exceptions.AppException;
import com.otsc.backend.mappers.MessageMapper;
import com.otsc.backend.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final UserService userService;
    private final BetService betService;

    public List<MessageDto> getChatByBetId(Long betId) {
        if (!betService.isBetPresent(betId)) {
            throw new AppException("Bet not found", HttpStatus.BAD_REQUEST);
        }

        List<Message> messages = messageRepository.findAllByBetIdOrderByIdAsc(betId);
        return messageMapper.messagesToMessageDtos(messages);
    }

    public MessageDto addMessage(MessageDto messageDto, String sender){
        userService.findByLogin(sender);
        messageDto.setName(sender);
        if (!(messageDto.getAlignment().equals("right") || messageDto.getAlignment().equals("left"))){
            throw new AppException("Wrong alignment", HttpStatus.BAD_REQUEST);
        }
        if (!betService.isBetPresent(messageDto.getBetId())){
            throw new AppException("Bet not found", HttpStatus.BAD_REQUEST);
        }

        Message message = messageMapper.messageDtoToMessage(messageDto);
        messageRepository.save(message);
        return messageDto;
    }

}
