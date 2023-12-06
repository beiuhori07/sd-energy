package com.sd.monitoring.service;

import com.sd.monitoring.dto.HourlyDataDto;
import com.sd.monitoring.entity.HourlyData;
import com.sd.monitoring.mapper.HourlyDataMapper;
import com.sd.monitoring.repository.HourlyDataRepository;
import com.sd.monitoring.repository.SensorReadingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HourlyDataService {

    private final HourlyDataRepository hourlyDataRepository;
    private final SensorReadingRepository sensorReadingRepository;

    public List<HourlyDataDto> getHourlyData(UUID deviceId, String date) {
        System.out.println("date string: " + date);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        System.out.println("Formatted LocalDate: " + localDate);

        List<HourlyData> hourlyData = hourlyDataRepository.findAllByDateAndDeviceId(localDate, deviceId);

        return hourlyData.stream()
                .sorted(Comparator.comparing(HourlyData::getDate))
                .map(HourlyDataMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void handleDeviceDeletion(UUID deviceId) {
    System.out.println("about to delete all hourly data for device " + deviceId);
        hourlyDataRepository.deleteAllByDeviceId(deviceId);
        sensorReadingRepository.deleteAllByDeviceId(deviceId);
    }
}
