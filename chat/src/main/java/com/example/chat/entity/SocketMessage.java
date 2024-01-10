package com.example.chat.entity;

import lombok.Builder;

@Builder
public class SocketMessage {

    private String sender;

    private String destination;

    private String senderName;

    private String destinationType;

    private String type;

    private String text;
}

