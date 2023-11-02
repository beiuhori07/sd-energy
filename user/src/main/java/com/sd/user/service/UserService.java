package com.sd.user.service;

import com.sd.user.dto.UserDto;
import com.sd.user.dto.UserIdsDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDto createUser(UserDto userToCreate);

    UserDto getUser(UUID id);

    List<UserDto> getUsers();

    UserDto updateUser(UUID id, UserDto userToUpdateWith);

    void deleteUser(UUID id);

    List<UserIdsDto> getUserIds();
}
