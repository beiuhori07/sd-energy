package com.sd.user.controller;

import com.sd.user.dto.UserDto;
import com.sd.user.dto.UserIdsDto;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
public interface UserApi {

    String USER_PREFIX = "/user";

    //create
    @PostMapping(USER_PREFIX)
    UserDto createUser(@Validated @RequestBody UserDto user);

    //read
    @GetMapping(USER_PREFIX + "/{id}")
    UserDto getUser(@PathVariable UUID id);

    //list
    @GetMapping(USER_PREFIX)
    List<UserDto> getUsers();

    //update
    @PutMapping(USER_PREFIX + "/{id}")
    UserDto updateUser(@PathVariable UUID id, @RequestBody @Valid UserDto userToUpdateWith);

    //delete
    @DeleteMapping(USER_PREFIX + "/{id}")
    void deleteUser(@PathVariable UUID id);

    @GetMapping(USER_PREFIX + "/ids")
    List<UserIdsDto> getUserIds();
}
