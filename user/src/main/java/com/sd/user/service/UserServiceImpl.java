package com.sd.user.service;

import com.sd.user.config.JwtService;
import com.sd.user.dto.UserDto;
import com.sd.user.dto.UserIdsDto;
import com.sd.user.entity.User;
import com.sd.user.exceptions.UserNotFoundException;
import com.sd.user.mapper.UserMapper;
import com.sd.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    @Override
    public UserDto createUser(UserDto userToCreate) {
        User user = User.builder()
                .name(userToCreate.getName())
                .role(userToCreate.getRole())
                .build();

        userRepository.saveAndFlush(user);
        return UserMapper.toDto(user);
    }

    @Override
    public UserDto getUser(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return UserMapper.toDto(user.get());
        } else {
            throw new UserNotFoundException("No user with id: " + id);
        }
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public UserDto updateUser(UUID id, UserDto userToUpdateWith) {
        Optional<User> user = userRepository.findById(id);
        log.info(userToUpdateWith.getName());
        log.info(String.valueOf(userToUpdateWith.getRole()));
        if (user.isPresent()) {
            User foundUser = user.get();
            foundUser.setName(userToUpdateWith.getName());
            foundUser.setRole(userToUpdateWith.getRole());
            userRepository.saveAndFlush(foundUser);

            return UserMapper.toDto(foundUser);
        } else {
            throw new UserNotFoundException("No user with id: " + id);
        }
    }

    @Override
    public void deleteUser(UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String bearerToken = jwtService.generateToken(userDetails);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + bearerToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://device-app:8082/device/user/" + id,
                HttpMethod.DELETE,
                requestEntity,
                String.class
        );


        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("DELETE request to device service successful");
            userRepository.deleteById(id);
            log.info("User deleted successfully");
        } else {
            log.error("DELETE request to device service failed with status code: " + responseEntity.getStatusCode());
        }

    }

    @Override
    public List<UserIdsDto> getUserIds() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> UserIdsDto.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .build())
                .toList();
    }
}
