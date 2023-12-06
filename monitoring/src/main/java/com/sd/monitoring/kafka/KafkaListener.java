package com.sd.monitoring.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sd.monitoring.service.HourlyDataService;
import com.sd.monitoring.service.ReadSensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class KafkaListener {

  private final ObjectMapper mapper = new ObjectMapper();
  private final ReadSensorService readSensorService;
  private final HourlyDataService hourlyDataService;

  @org.springframework.kafka.annotation.KafkaListener(topics = "energy-topic", groupId = "my-group")
  public void consumeSensorReading(String message) throws JsonProcessingException {
    System.out.println("got message = " + message);
    KafkaMessage kafkaMessage = mapper.readValue(message, KafkaMessage.class);
    System.out.println("Received message: " + kafkaMessage.toString());
    readSensorService.readNewSensorValue(kafkaMessage);
  }

  @org.springframework.kafka.annotation.KafkaListener(
      topics = "device-changes-topic",
      groupId = "my-group")
  public void consumeDeviceChange(String message) throws JsonProcessingException {
    System.out.println("got message = " + message);
    //        KafkaMessage kafkaMessage = mapper.readValue(message, KafkaMessage.class);
    //        System.out.println("Received message: " + kafkaMessage.toString());
    //        readSensorService.readNewSensorValue(kafkaMessage);
    hourlyDataService.handleDeviceDeletion(UUID.fromString(message));
  }
}
