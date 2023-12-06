package com.sd.monitoring.mapper;

import com.sd.monitoring.dto.HourlyDataDto;
import com.sd.monitoring.entity.HourlyData;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HourlyDataMapper {

    public HourlyDataDto mapToDto(HourlyData hourlyData) {
    return HourlyDataDto.builder()
        .id(hourlyData.getId())
        .date(hourlyData.getDate().toLocalDate())
        .hour(hourlyData.getDate().getHour())
        .minute(hourlyData.getDate().getMinute())
        .value(hourlyData.getValue())
        .deviceId(hourlyData.getDeviceId())
        .build();
    }

}
