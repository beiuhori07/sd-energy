package com.sd.device.repository;

import com.sd.device.entity.Device;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
    List<Device> findAllByUserId(UUID userId);
}
