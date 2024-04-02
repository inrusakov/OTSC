package com.otsc.backend.mappers;

import com.otsc.backend.dtos.MessageDto;
import com.otsc.backend.entities.Message;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message messageDtoToMessage(MessageDto messageDto);
    List<Message> messageDtosToMessages(List<MessageDto> messageDtoList);
    MessageDto messageToMessageDto(Message message);
    List<MessageDto> messagesToMessageDtos(List<Message> messageList);
}
