package com.otsc.backend.services;

import com.otsc.backend.dtos.MessageDto;
import com.otsc.backend.dtos.UserDto;
import com.otsc.backend.entities.Message;
import com.otsc.backend.exceptions.AppException;
import com.otsc.backend.mappers.MessageMapper;
import com.otsc.backend.repositories.MessageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {
    @Mock
    private MessageRepository messageRepository;
    private MessageMapper messageMapper = Mappers.getMapper(MessageMapper.class);
    ;
    @Mock
    private UserService userService;
    @Mock
    private BetService betService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testGetChatByBetId_ExistingBet() {
        String betId = "123";
        List<Message> messages = new ArrayList<>(); // Mock messages for the bet

        // Mock behavior for betService.isBetPresent
        when(betService.isBetPresent(betId)).thenReturn(true);

        // Mock behavior for messageRepository.findAllByBetIdOrderByTimeAsc
        when(messageRepository.findAllByBetIdOrderByTimeAsc(betId)).thenReturn(messages);

        // Mock behavior for messageMapper (optional)

        MessageService messageService = new MessageService(messageRepository, messageMapper, userService, betService);

        List<MessageDto> chatMessages = messageService.getChatByBetId(betId);

        // Assertions
        assertNotNull(chatMessages);
        assertEquals(messages.size(), chatMessages.size());
    }

    @Test(expected = AppException.class)
    public void testGetChatByBetId_NonexistentBet() {
        String betId = "456";

        // Mock behavior for betService.isBetPresent
        when(betService.isBetPresent(betId)).thenReturn(false);

        MessageService messageService = new MessageService(messageRepository, messageMapper, userService, betService);

        messageService.getChatByBetId(betId);
    }

    @Test
    public void testAddMessage_ValidMessage() {
        String betId = "123";
        String sender = "senderLogin";
        String message = "Test message";
        String alignment = "left";

        // Mock behavior for userService.findByLogin
        UserDto userDto = new UserDto();
        when(userService.findByLogin(sender)).thenReturn(userDto);

        // Mock behavior for betService.isBetPresent
        when(betService.isBetPresent(betId)).thenReturn(true);

        // Mock behavior for messageMapper (optional)

        MessageService messageService = new MessageService(messageRepository, messageMapper, userService, betService);

        MessageDto messageDto = new MessageDto();
        messageDto.setText(message);
        messageDto.setAlignment(alignment);
        messageDto.setBetId(betId);
        MessageDto addedMessage = messageService.addMessage(messageDto, sender);

        // Assertions
        assertNotNull(addedMessage);
        assertEquals(message, addedMessage.getText());
        assertEquals(alignment, addedMessage.getAlignment());
        assertEquals(sender, addedMessage.getName());
    }

    @Test(expected = AppException.class)
    public void testAddMessage_InvalidAlignment() {
        String betId = "123";
        String sender = "senderLogin";
        String message = "Test message";
        String invalidAlignment = "invalid";

        // Mock behavior (as needed)

        MessageService messageService = new MessageService(messageRepository, messageMapper, userService, betService);

        MessageDto messageDto = new MessageDto();
        messageDto.setText(message);
        messageDto.setAlignment(invalidAlignment);
        messageDto.setBetId(betId);
        messageService.addMessage(messageDto, sender);
    }

    @Test(expected = AppException.class)
    public void testAddMessage_NonexistentBet() {
        String betId = "456";
        String sender = "senderLogin";
        String message = "Test message";
        String alignment = "left";

        // Mock behavior for userService.findByLogin

        MessageService messageService = new MessageService(messageRepository, messageMapper, userService, betService);

        MessageDto messageDto = new MessageDto();
        messageDto.setText(message);
        messageDto.setAlignment(alignment);
        messageDto.setBetId(betId);
        messageService.addMessage(messageDto, sender);
    }

}