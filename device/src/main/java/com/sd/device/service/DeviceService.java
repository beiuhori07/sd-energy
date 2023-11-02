package com.sd.device.service;

import com.sd.device.dto.DeviceDto;
import java.util.List;
import java.util.UUID;

public interface DeviceService {

    DeviceDto createDevice(DeviceDto deviceToCreate);

    DeviceDto getDevice(UUID id);

    List<DeviceDto> getDevices();

    List<DeviceDto> getUserDevices(UUID userId);

    void deleteUserDevices(UUID userId);

    List<DeviceDto> getUnassignedDevices();

    DeviceDto updateDevice(UUID id, DeviceDto deviceToUpdateWith);

    void deleteDevice(UUID id);
}
