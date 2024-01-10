package com.example.chat.service;

import com.example.chat.entity.SocketMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendEvent(UUID deviceId, LocalDateTime date) {

            SocketMessage message = SocketMessage.builder().text("TEST TEST TETST").build();
    //
//     SocketMessage.builder().deviceId(deviceId).hour(date.getHour()).minute(date.getMinute()).build();

    //        messagingTemplate.convertAndSend("/topic/notifications", message);
    messagingTemplate.convertAndSend("/topic/notifications", message);
    }
}
