package com.sd.monitoring.entity;

import java.util.UUID;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocketMessage {

    private UUID deviceId;
    private int hour;
    private int minute;
}
