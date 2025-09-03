package co.com.pragma.consumer.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record UserResponseDto(
        Long id,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String address,
        String phone,
        String email,
        BigDecimal baseSalary,
        LocalDateTime creationDate
) {}
