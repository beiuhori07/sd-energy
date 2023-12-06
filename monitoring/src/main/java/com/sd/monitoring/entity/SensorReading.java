package com.sd.monitoring.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sensor_reading")
public class SensorReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID deviceId;

    private LocalDateTime dateTime;

    private Float value;

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
