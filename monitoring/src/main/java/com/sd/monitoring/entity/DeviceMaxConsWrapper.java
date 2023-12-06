package com.sd.monitoring.entity;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceMaxConsWrapper {

    private Integer maxConsumption;
    private boolean alreadyEmittedEvent;
}
