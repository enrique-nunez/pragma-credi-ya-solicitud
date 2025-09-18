package co.com.pragma.r2dbc.dto;

import org.springframework.data.relational.core.mapping.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationFieldsPageDto {

    @Column("idSolicitud")
    private Long idSolicitud;

    @Column("montoSolicitado")
    private BigDecimal montoSolicitado;

    @Column("plazoMeses")
    private Integer plazoMeses;

    @Column("emailUsuario")
    private String emailUsuario;

    @Column("tipoPrestamo")
    private String tipoPrestamo;

    @Column("tasaInteres")
    private BigDecimal tasaInteres;

    @Column("estadoSolicitud")
    private String estadoSolicitud;

    @Column("deudaTotalMensual")
    private BigDecimal deudaTotalMensual;
}