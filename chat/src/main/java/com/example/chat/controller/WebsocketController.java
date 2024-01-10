package com.example.chat.controller;

import com.example.chat.entity.SocketMessage;
import com.example.chat.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class WebsocketController {

    private final WebSocketService webSocketService;

    @MessageMapping("/hello")
    @SendTo("/topic/notifications")
    public String greeting(String message) {
        System.out.println(message);
        return message;
    }

    @GetMapping(  "/ws-trigger")
    void triggerWs() {
        webSocketService.sendEvent(UUID.randomUUID(), LocalDateTime.now());
    }
}
