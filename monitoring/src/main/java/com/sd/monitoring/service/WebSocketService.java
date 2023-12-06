package com.sd.monitoring.service;

import com.sd.monitoring.entity.SocketMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WebSocketService {

  private final SimpMessagingTemplate messagingTemplate;

  public void sendEvent(UUID deviceId, LocalDateTime date) {

    SocketMessage message =
        SocketMessage.builder().deviceId(deviceId).hour(date.getHour()).minute(date.getMinute()).build();

    messagingTemplate.convertAndSend("/topic/notifications", message);
  }
}
