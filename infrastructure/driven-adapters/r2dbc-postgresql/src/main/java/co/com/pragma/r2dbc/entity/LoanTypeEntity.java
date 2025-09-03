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
@Table("tipo_prestamo")
public class LoanTypeEntity {
    @Id
    @Column("id_tipo_prestamo")
    private Long loanTypeId;

    @Column("nombre")
    private String name;

    @Column("monto_maximo")
    private BigDecimal maxAmount;

    @Column("tasa_interes")
    private BigDecimal interestRate;

    @Column("validacion_automatica")
    private Boolean automaticValidation;
}
