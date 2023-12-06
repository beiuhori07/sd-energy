package com.sd.monitoring.kafka;

import java.util.UUID;

public class KafkaMessage {

    public KafkaMessage(long timestamp, UUID deviceId, Float value) {
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.value = value;
    }
    public KafkaMessage() {
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public String toString() {
        return "KafkaMessage{" +
                "timestamp=" + timestamp +
                ", deviceId='" + deviceId + '\'' +
                ", value=" + value +
                '}';
    }

    private Long timestamp;
    private UUID deviceId;
    private Float value;
}
