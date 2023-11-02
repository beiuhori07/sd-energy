package com.sd.device.controller;

import com.sd.device.dto.DeviceDto;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
public interface DeviceApi {

    String DEVICE_PREFIX = "/device";

    //create
    @PostMapping(DEVICE_PREFIX)
    DeviceDto createDevice(@RequestBody DeviceDto device);

    //read
    @GetMapping(DEVICE_PREFIX + "/{id}")
    DeviceDto getDevice(@PathVariable UUID id);

    //list
    @GetMapping(DEVICE_PREFIX)
    List<DeviceDto> getDevices();

    //list
    @GetMapping(DEVICE_PREFIX + "/user/{userId}")
    List<DeviceDto> getUserDevices(@PathVariable UUID userId);

    @DeleteMapping(DEVICE_PREFIX + "/user/{userId}")
    void deleteUserDevices(@PathVariable UUID userId);


    @GetMapping(DEVICE_PREFIX + "/user/unassigned")
    List<DeviceDto> getUnassignedDevices();

    //update
    @PutMapping(DEVICE_PREFIX + "/{id}")
    DeviceDto updateDevices(@PathVariable UUID id, @RequestBody @Valid DeviceDto deviceToUpdateWith);

    //delete
    @DeleteMapping(DEVICE_PREFIX + "/{id}")
    void deleteDevices(@PathVariable UUID id);
}
