package com.otsc.backend.controllers;

import com.otsc.backend.dtos.AvatarDto;
import com.otsc.backend.dtos.CredentialChangeDto;
import com.otsc.backend.dtos.ProfileDto;
import com.otsc.backend.dtos.UserDto;
import com.otsc.backend.entities.User;
import com.otsc.backend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PutMapping("/avatar")
    public ResponseEntity<ProfileDto> updateAvatar(@RequestBody AvatarDto avatar) {
        UserDto principal = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ProfileDto updatedProfile = userService.changeAvatar(principal.getLogin(), avatar.avatar());
        return ResponseEntity.ok(updatedProfile);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileDto> getProfile() {
        UserDto principal = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ProfileDto updatedProfile = userService.getProfileInfo(principal.getLogin());
        updatedProfile.setLogin(principal.getLogin());
        return ResponseEntity.ok(updatedProfile);
    }

    @GetMapping("/getProfileById")
    public ResponseEntity<ProfileDto> getProfileById(@RequestParam String userId) {
        ProfileDto updatedProfile = userService.getProfileById(userId);
        return ResponseEntity.ok(updatedProfile);
    }

    @PostMapping("/changePass")
    public ResponseEntity<UserDto> changePassword(@RequestBody @Valid CredentialChangeDto changeDto){
        UserDto principal = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto updatedDto = userService.changePass(changeDto, principal.getLogin());
        return ResponseEntity.ok(updatedDto);
    }
    //TODO: Notification settings update
}
