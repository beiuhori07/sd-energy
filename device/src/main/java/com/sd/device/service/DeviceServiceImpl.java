package com.sd.device.service;

import com.sd.device.dto.DeviceDto;
import com.sd.device.entity.Device;
import com.sd.device.mapper.DeviceMapper;
import com.sd.device.repository.DeviceRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public DeviceDto createDevice(DeviceDto deviceToCreate) {
        Device device = DeviceMapper.toDevice(deviceToCreate);

        deviceRepository.saveAndFlush(device);
        return DeviceMapper.toDto(device);
    }

    @Override
    public DeviceDto getDevice(UUID id) {
        Optional<Device> user = deviceRepository.findById(id);
        if(user.isPresent()) {
            return DeviceMapper.toDto(user.get());
        } else {
            throw new RuntimeException("No user with id: " + id);
        }
    }

    @Override
    public List<DeviceDto> getDevices() {
        List<Device> devices = deviceRepository.findAll();
        return devices.stream()
                .map(DeviceMapper::toDto)
                .toList();
    }

    @Override
    public List<DeviceDto> getUserDevices(UUID userId) {
        List<Device> devices = deviceRepository.findAllByUserId(userId);
        return devices.stream()
                .map(DeviceMapper::toDto)
                .toList();
    }

    @Override
    public void deleteUserDevices(UUID userId) {
        List<Device> devices = deviceRepository.findAllByUserId(userId);
        devices.forEach(device -> device.setUserId(null));

        deviceRepository.saveAllAndFlush(devices);
    }

    @Override
    public List<DeviceDto> getUnassignedDevices() {
        List<Device> devices = deviceRepository.findAllByUserId(null);
        return devices.stream()
                .map(DeviceMapper::toDto)
                .toList();
    }

    @Override
    public DeviceDto updateDevice(UUID id, DeviceDto deviceToUpdateWith) {
        Optional<Device> device = deviceRepository.findById(id);
        if(device.isPresent()) {
            Device foundDevice = device.get();
            foundDevice.setDescription(deviceToUpdateWith.getDescription());
            foundDevice.setAddress(deviceToUpdateWith.getAddress());
            foundDevice.setMaxConsumption(deviceToUpdateWith.getMaxConsumption());
            foundDevice.setUserId(deviceToUpdateWith.getUserId());
            deviceRepository.saveAndFlush(foundDevice);

            return DeviceMapper.toDto(foundDevice);
        } else {
            throw new RuntimeException("No user with id: " + id);
        }
    }

    @Override
    public void deleteDevice(UUID id) {
        deviceRepository.deleteById(id);

        // send kafka event to delete all hourly data entries
        kafkaProducerService.sendMessage(String.valueOf(id));
    }
}
