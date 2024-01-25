package com.otsc.backend.mappers;

import com.otsc.backend.dtos.SignUpDto;
import com.otsc.backend.dtos.UserDto;
import com.otsc.backend.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

}
