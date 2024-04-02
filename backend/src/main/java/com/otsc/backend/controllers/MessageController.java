package com.otsc.backend.controllers;

import com.otsc.backend.dtos.MessageDto;
import com.otsc.backend.dtos.UserDto;
import com.otsc.backend.entities.Message;
import com.otsc.backend.services.MessageService;
import com.otsc.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;

    @GetMapping("/getChat")
    public ResponseEntity<List<MessageDto>> getChatByBetId(@RequestParam Long betId) {
        List<MessageDto> chatByBetId = messageService.getChatByBetId(betId);
        return ResponseEntity.ok(chatByBetId);
    }

    @PostMapping("/addMessage")
    public ResponseEntity<MessageDto> addMessage(@RequestBody MessageDto message) {
        UserDto principal = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto user = userService.findByLogin(principal.getLogin());
        MessageDto messageDto = messageService.addMessage(message, user.getLogin());
        return ResponseEntity.ok(messageDto);
    }
}
