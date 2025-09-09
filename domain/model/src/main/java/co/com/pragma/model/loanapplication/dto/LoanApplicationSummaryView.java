package co.com.pragma.model.loanapplication.dto;

import java.math.BigDecimal;

public record LoanApplicationSummaryView(
        Long idSolicitud,
        BigDecimal montoSolicitado,
        Integer plazoMeses,
        String emailUsuario,
        String tipoPrestamo,
        BigDecimal tasaInteres,
        String estadoSolicitud
) {}