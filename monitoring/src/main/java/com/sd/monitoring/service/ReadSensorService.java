package com.sd.monitoring.service;

//import com.sd.monitoring.config.JwtService;
import com.sd.monitoring.dto.DeviceDto;
import com.sd.monitoring.entity.DeviceMaxConsWrapper;
import com.sd.monitoring.entity.HourlyData;
import com.sd.monitoring.entity.SensorReading;
import com.sd.monitoring.kafka.KafkaMessage;
import com.sd.monitoring.repository.HourlyDataRepository;
import com.sd.monitoring.repository.SensorReadingRepository;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
@Slf4j
public class ReadSensorService {

  @Value("${reading-timeframe}")
  private int timeframe;

  private final SensorReadingRepository sensorReadingRepository;
  private final HourlyDataRepository hourlyDataRepository;
  private final WebSocketService webSocketService;
  private final RestTemplate restTemplate;
//private final JwtService jwtService;
  private static final Map<UUID, DeviceMaxConsWrapper> deviceMaxConsMapping = new HashMap<>();

  @Transactional
  public void readNewSensorValue(KafkaMessage message) {
    Optional<SensorReading> latestDeviceReading =
        sensorReadingRepository.findTopByDeviceIdOrderByDateTimeDesc(message.getDeviceId());
    LocalDateTime latestLocalDateTime = getLocalDateTime(message.getTimestamp());

    boolean shouldKeepSameGroup = true;
    if (latestDeviceReading.isPresent()) {
      SensorReading latestSensorReading = latestDeviceReading.get();
      if (!isWithinSameTimeFrame(latestSensorReading.getDateTime(), message.getTimestamp())) {
        shouldKeepSameGroup = false;
        latestLocalDateTime = latestDeviceReading.get().getDateTime();
      }
    }

    LocalDateTime messageLocalDateTime = getLocalDateTime(message.getTimestamp());
    DeviceMaxConsWrapper deviceMaxCons = getDeviceMaxCons(message.getDeviceId());

    List<SensorReading> readings = sensorReadingRepository.findAllByDeviceId(message.getDeviceId());
    Float hourlyValue = getReadingsSum(readings);

    System.out.println("current hourly value = " + hourlyValue);

    SensorReading sensorReading =
        buildSensorReading(message.getValue(), message.getDeviceId(), messageLocalDateTime);

    if (shouldKeepSameGroup) {
      sensorReadingRepository.save(sensorReading);
      hourlyValue = hourlyValue + sensorReading.getValue();

      if (hourlyValue > deviceMaxCons.getMaxConsumption()) {
        System.out.println("hourly value = " + hourlyValue);
        System.out.println("device max cons = " + deviceMaxCons.getMaxConsumption());

        System.out.println("about to send event over ws");
        if (!deviceMaxCons.isAlreadyEmittedEvent()) {
          webSocketService.sendEvent(message.getDeviceId(), messageLocalDateTime);
          deviceMaxCons.setAlreadyEmittedEvent(true);
        }
      }
    } else {
      sensorReadingRepository.deleteAllByDeviceId(message.getDeviceId());

      sensorReadingRepository.save(sensorReading);

      HourlyData hourlyData =
          buildHourlyData(hourlyValue, message.getDeviceId(), latestLocalDateTime);
      hourlyDataRepository.save(hourlyData);

      deviceMaxCons.setAlreadyEmittedEvent(false);
    }
  }

  private boolean isWithinSameTimeFrame(LocalDateTime latestDateTime, long timestamp) {
    LocalDateTime messageLocalDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

    System.out.println("timestamp to datetime conv = ");
    System.out.println(messageLocalDateTime.toString());

    System.out.println("latest datetime conv = ");
    System.out.println(latestDateTime.toString());

    if (messageLocalDateTime.toLocalDate().equals(messageLocalDateTime.toLocalDate())) {
      if (messageLocalDateTime.getHour() == latestDateTime.getHour()
          && messageLocalDateTime.getMinute() == latestDateTime.getMinute()) {
        System.out.println("times are from within the same timeframe");
        return true;
      }
    }

    return false;
  }

  private DeviceMaxConsWrapper getDeviceMaxCons(UUID deviceId) {
    if (deviceMaxConsMapping.containsKey(deviceId)) {

      // cached
      return deviceMaxConsMapping.get(deviceId);
    } else {
//      String bearerToken = jwtService.generateToken();
      HttpHeaders headers = new HttpHeaders();
//      headers.set("Authorization", "Bearer " + bearerToken);

      HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

      ResponseEntity<DeviceDto> responseEntity = restTemplate.exchange(
              "http://device-app:8082/device/" + deviceId,
              HttpMethod.GET,
              requestEntity,
              DeviceDto.class);






              // todo: change url
//      vResponseEntity<DeviceDto> responseEntity =
//          restTemplate.getForEntity("http://device-app:8082/device/" + deviceId, DeviceDto.class);

      if (responseEntity.getStatusCode().is2xxSuccessful()) {
        log.info("GET request to device service successful");
      } else {
        log.error(
            "GET request to device service failed with status code: "
                + responseEntity.getStatusCode());
      }

      if (responseEntity.getBody() != null) {
        deviceMaxConsMapping.put(
            deviceId,
            DeviceMaxConsWrapper.builder()
                .maxConsumption(responseEntity.getBody().getMaxConsumption())
                .alreadyEmittedEvent(false)
                .build());
        return deviceMaxConsMapping.get(deviceId);
      } else {
        throw new RuntimeException("no device found with id: " + deviceId);
      }
    }
  }

  private LocalDateTime getLocalDateTime(long timestamp) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
  }

  private Float getReadingsSum(List<SensorReading> readings) {
    return readings.stream().map(SensorReading::getValue).reduce(0f, Float::sum);
  }

  private HourlyData buildHourlyData(Float hourlyValue, UUID deviceId, LocalDateTime localDateTime) {
    return HourlyData.builder().value(hourlyValue).deviceId(deviceId).date(localDateTime).build();
  }

  private SensorReading buildSensorReading(Float value, UUID deviceId, LocalDateTime dateTime) {
    return SensorReading.builder().value(value).deviceId(deviceId).dateTime(dateTime).build();
  }
}
