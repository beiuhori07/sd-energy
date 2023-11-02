package com.sd.device.controller;

import com.sd.device.dto.DeviceDto;
import com.sd.device.service.DeviceService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController("/device")
@RequiredArgsConstructor
@Slf4j
public class DeviceController implements DeviceApi{

    private final DeviceService deviceService;

    @Override
    public DeviceDto createDevice(DeviceDto user) {
        log.info("Received request to create a device");
        return deviceService.createDevice(user);
    }

    @Override
    public DeviceDto getDevice(UUID id) {
        log.info("Received request to get a device with id: " + id);
        return deviceService.getDevice(id);
    }

    @Override
    public List<DeviceDto> getDevices() {
        log.info("Received request to list all devices");
        return deviceService.getDevices();
    }

    @Override
    public List<DeviceDto> getUserDevices(UUID userId) {
        log.info("Received request to list all devices belonging to user with id: " + userId);
        return deviceService.getUserDevices(userId);
    }

    @Override
    public void deleteUserDevices(UUID userId) {
        log.info("Received request to remove user link from all device with userId: " + userId);
        deviceService.deleteUserDevices(userId);
    }

    @Override
    public List<DeviceDto> getUnassignedDevices() {
        log.info("Received request to list all unassigned devices");
        return deviceService.getUnassignedDevices();
    }

    @Override
    public DeviceDto updateDevices(UUID id, DeviceDto deviceToUpdateWith) {
        log.info("Received request to update device with id: " + id);
        return deviceService.updateDevice(id, deviceToUpdateWith);
    }

    @Override
    public void deleteDevices(UUID id) {
        log.info("Received request to delete device with id: " + id);
        deviceService.deleteDevice(id);
    }
}
