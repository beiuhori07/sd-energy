package com.sd.device.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DeviceDto {

    @NotNull
    private UUID id;

    private String description;

    private String address;

    @NotNull
    private Integer maxConsumption;

    private UUID userId;
}
