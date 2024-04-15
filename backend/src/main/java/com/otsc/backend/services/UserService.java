package com.otsc.backend.services;

import com.otsc.backend.dtos.CredentialChangeDto;
import com.otsc.backend.dtos.CredentialsDto;
import com.otsc.backend.dtos.ProfileDto;
import com.otsc.backend.dtos.SignUpDto;
import com.otsc.backend.dtos.UserDto;
import com.otsc.backend.entities.User;
import com.otsc.backend.exceptions.AppException;
import com.otsc.backend.mappers.UserMapper;
import com.otsc.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.IllegalFormatCodePointException;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByLogin(credentialsDto.login())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByLogin(userDto.login());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.password())));

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }

    public UserDto findByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    public ProfileDto getProfileInfo(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.userToProfileDto(user);
    }

    public ProfileDto getProfileById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.userToProfileDto(user);
    }

    public ProfileDto changeProfileInfo(String login, ProfileDto profileDto) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        if (!profileDto.getFirstName().isEmpty()) {
            user.setFirstName(profileDto.getFirstName());
        }
        if (!profileDto.getLastName().isEmpty()) {
            user.setLastName(profileDto.getLastName());
        }
        if (!profileDto.getStatus().isEmpty()) {
            user.setStatus(profileDto.getStatus());
        }
        userRepository.save(user);
        return userMapper.userToProfileDto(user);
    }

    public UserDto changePass(CredentialChangeDto request, String sender){
        User user = userRepository.findByLogin(request.getLogin())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        userRepository.findByLogin(sender)
                .orElseThrow(() -> new AppException("Unknown sender", HttpStatus.NOT_FOUND));
        if (!Objects.equals(request.getLogin(), sender)){
            throw new AppException("Sender is not equal to account owner", HttpStatus.BAD_REQUEST);
        }
        if (!Objects.equals(request.getOldPass(), request.getNewPass())){
            throw new AppException("Old pass is not equal to new one", HttpStatus.BAD_REQUEST);
        }
        if (!passwordEncoder.matches(CharBuffer.wrap(request.getOldPass()), user.getPassword())) {
            throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(request.getNewPass())));

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }

    public ProfileDto changeAvatar(String login, String avatar) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        if (avatar != null) {
            user.setAvatar(avatar);
        }
        userRepository.save(user);
        return userMapper.userToProfileDto(user);
    }

}
