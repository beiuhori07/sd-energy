package com.sd.monitoring.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  public String greeting(String message) {
    System.out.println(message);
    return "Hello, " + message + "!";
  }
}
