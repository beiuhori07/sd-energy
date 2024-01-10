package com.sd.device.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "device-changes-topic";

    public void sendMessage(String message) {

        kafkaTemplate.send(TOPIC, message);
        System.out.println("Message sent to Kafka: " + message);
    }

}

