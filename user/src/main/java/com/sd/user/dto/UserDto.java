package com.sd.user.dto;

import com.sd.user.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDto {

    @NotNull
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private Role role;
}
