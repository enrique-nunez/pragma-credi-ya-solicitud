package co.com.pragma.api.dto;

import jakarta.validation.constraints.NotBlank;

public record StatusRequestDto(
        @NotBlank(message = "status is required")
        String status
){}