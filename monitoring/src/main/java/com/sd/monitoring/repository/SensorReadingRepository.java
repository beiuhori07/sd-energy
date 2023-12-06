package com.sd.monitoring.repository;

import com.sd.monitoring.entity.SensorReading;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {

    // Query method to find the latest entry for a certain user
    Optional<SensorReading> findTopByDeviceIdOrderByDateTimeDesc(UUID deviceId);

    List<SensorReading> findAllByDeviceId(UUID deviceId);

    void deleteAllByDeviceId(UUID deviceId);
}
