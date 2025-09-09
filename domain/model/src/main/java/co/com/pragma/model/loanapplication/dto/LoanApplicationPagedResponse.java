package co.com.pragma.model.loanapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Builder(toBuilder = true)
public class LoanApplicationPagedResponse {
    private Long idSolicitud;
    private BigDecimal montoSolicitado;
    private Integer plazoMeses;
    private String emailUsuario;
    private String tipoPrestamo;
    private BigDecimal tasaInteres;
    private String estadoSolicitud;


    @ConstructorProperties({
            "idSolicitud", "montoSolicitado", "plazoMeses", "emailUsuario",
            "tipoPrestamo", "tasaInteres", "estadoSolicitud"
    })
    public LoanApplicationPagedResponse(Long idSolicitud, BigDecimal montoSolicitado,
                                        Integer plazoMeses, String emailUsuario,
                                        String tipoPrestamo, BigDecimal tasaInteres,
                                        String estadoSolicitud) {
        this.idSolicitud = idSolicitud;
        this.montoSolicitado = montoSolicitado;
        this.plazoMeses = plazoMeses;
        this.emailUsuario = emailUsuario;
        this.tipoPrestamo = tipoPrestamo;
        this.tasaInteres = tasaInteres;
        this.estadoSolicitud = estadoSolicitud;
    }
}