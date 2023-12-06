package com.sd.monitoring.repository;

import com.sd.monitoring.entity.HourlyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface HourlyDataRepository extends JpaRepository<HourlyData, Long> {

    void deleteAllByDeviceId(UUID deviceId);

    @Query("SELECT e FROM HourlyData e WHERE DATE(e.date) = :targetDate AND e.deviceId = :deviceId")
    List<HourlyData> findAllByDateAndDeviceId(@Param("targetDate") LocalDate targetDate, @Param("deviceId") UUID deviceId);
}
