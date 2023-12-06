package com.sd.monitoring.dto;

import java.util.UUID;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DeviceDto {

    private UUID id;

    private String description;

    private String address;

    private Integer maxConsumption;

    private UUID userId;
}
