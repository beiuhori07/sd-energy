package com.sd.user.mapper;

import com.sd.user.dto.UserDto;
import com.sd.user.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

    public static User toUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .role(userDto.getRole())
                .build();
    }
}
