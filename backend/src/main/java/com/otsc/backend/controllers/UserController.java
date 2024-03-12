package com.otsc.backend.controllers;

import com.otsc.backend.dtos.ProfileDto;
import com.otsc.backend.dtos.UserDto;
import com.otsc.backend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PutMapping("/profile")
    public ResponseEntity<ProfileDto> updateProfile(@RequestBody @Valid ProfileDto profileDto) {
        UserDto principal = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ProfileDto updatedProfile = userService.changeProfileInfo(principal.getLogin(), profileDto);
        return ResponseEntity.ok(updatedProfile);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileDto> getProfile() {
        UserDto principal = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ProfileDto updatedProfile = userService.getProfileInfo(principal.getLogin());
        return ResponseEntity.ok(updatedProfile);
    }
    //TODO: Change pass
    //TODO: Notification settings update
}
