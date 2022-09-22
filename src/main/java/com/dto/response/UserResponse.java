package com.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private LocalDate created;
    private boolean isActive = true;
}

