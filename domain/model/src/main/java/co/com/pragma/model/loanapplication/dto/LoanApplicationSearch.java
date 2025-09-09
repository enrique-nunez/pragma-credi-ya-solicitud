package co.com.pragma.model.loanapplication.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class LoanApplicationSearch {
    private Integer idSolicitud;
    private BigDecimal monto;
    private Integer plazo;
    private String email;
    private String tipoPrestamo;
    private BigDecimal tasaInteres;
    private String estado;
    private String nombre;
    private BigDecimal salarioBase;
    private BigDecimal montoMensualSolicitud;
}
