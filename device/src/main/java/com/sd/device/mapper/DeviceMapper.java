package com.sd.device.mapper;

import com.sd.device.dto.DeviceDto;
import com.sd.device.entity.Device;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DeviceMapper {

    public static DeviceDto toDto(Device device) {
    return DeviceDto.builder()
        .id(device.getId())
        .description(device.getDescription())
        .address(device.getAddress())
        .maxConsumption(device.getMaxConsumption())
        .userId(device.getUserId())
        .build();
    }

    public static Device toDevice(DeviceDto deviceDto) {
    return Device.builder()
        .id(deviceDto.getId())
        .description(deviceDto.getDescription())
        .address(deviceDto.getAddress())
        .maxConsumption(deviceDto.getMaxConsumption())
        .userId(deviceDto.getUserId())
        .build();
    }
}
