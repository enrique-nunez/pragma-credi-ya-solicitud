package co.com.pragma.r2dbc.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("solicitud")
public class LoanApplicationEntity {
    @Id
    @Column("id_solicitud")
    private Long idLoan;

    @Column("monto")
    private BigDecimal amount;

    @Column("plazo")
    private Integer term;

    @Column("email")
    private String email;

    @Column("id_estado")
    private Long idStatus;

    @Column("id_tipo_prestamo")
    private Long loanTypeId;
}
