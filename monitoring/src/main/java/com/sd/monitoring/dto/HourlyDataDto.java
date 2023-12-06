package com.sd.monitoring.dto;

import java.time.LocalDate;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class HourlyDataDto {

    private LocalDate date;

    private int hour;

    private int minute;

    private Long id;

    private UUID deviceId;

    private Float value;
}
