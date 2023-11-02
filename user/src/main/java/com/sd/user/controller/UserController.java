package com.sd.user.controller;

import com.sd.user.dto.UserDto;
import com.sd.user.dto.UserIdsDto;
import com.sd.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserApi{

    private final UserService userService;

    @Override
    public UserDto createUser(UserDto user) {
        log.info("Received request to create user");
        return userService.createUser(user);
    }

    @Override
    public UserDto getUser(UUID id) {
        log.info("Received request to get an user with id: " + id);
        return userService.getUser(id);
    }

    @Override
    public List<UserDto> getUsers() {
        log.info("Received request to list all users");
        return userService.getUsers();
    }

    @Override
    public UserDto updateUser(UUID id, UserDto userToUpdateWith) {
        log.info("Received request to update user with id: " + id);
        return userService.updateUser(id, userToUpdateWith);
    }

    @Override
    public void deleteUser(UUID id) {
        log.info("Received request to delete user with id: " + id);
        userService.deleteUser(id);
      }

    @Override
    public List<UserIdsDto> getUserIds() {
        log.info("Received request to get all user ids");
        return userService.getUserIds();
    }
}
