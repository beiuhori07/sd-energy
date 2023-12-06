package org.example;


import java.util.UUID;

//@Setter
//@Getter
//@AllArgsConstructor
public class KafkaMessage {

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public KafkaMessage(long timestamp, UUID deviceId, float value) {
         this.timestamp = timestamp;
         this.deviceId = deviceId;
         this.value = value;
     }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

        private long timestamp;
    private UUID deviceId;
    private float value;
}
