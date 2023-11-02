package com.sd.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Setter
@Getter
@Builder
public class UserIdsDto {

    private UUID id;
    private String name;
}
