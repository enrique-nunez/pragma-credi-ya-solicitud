package co.com.pragma.api.dto;

import java.math.BigDecimal;

public record LoanApplicationResponseDto(
        Long id,
        BigDecimal amount,
        Long loanTypeId,
        Long statusId,
        Integer term,
        String email,
        String identityDocument
) {}