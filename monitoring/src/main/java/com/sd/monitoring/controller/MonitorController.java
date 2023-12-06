package com.sd.monitoring.controller;



import com.sd.monitoring.dto.HourlyDataDto;
import com.sd.monitoring.entity.HourlyData;
import com.sd.monitoring.service.HourlyDataService;
import com.sd.monitoring.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController()
@RequiredArgsConstructor
@Slf4j
public class MonitorController {

    private final WebSocketService webSocketService;
    private final HourlyDataService hourlyDataService;

    private static final String MONITORING = "/monitoring";

    @GetMapping(MONITORING + "/ws-trigger")
    void triggerWs() {
        webSocketService.sendEvent(UUID.randomUUID(), LocalDateTime.now());
    }

    @GetMapping( MONITORING+ "/daily-hourly-data/{deviceId}/{date}")
    List<HourlyDataDto> getHourlyData(@PathVariable UUID deviceId, @PathVariable String date) {
        return hourlyDataService.getHourlyData(deviceId, date);
    }
}
