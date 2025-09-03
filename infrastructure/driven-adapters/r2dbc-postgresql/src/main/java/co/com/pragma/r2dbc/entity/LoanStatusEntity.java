package co.com.pragma.r2dbc.entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("estados")
public class LoanStatusEntity {
    @Id
    @Column("id_estado")
    private Long idStatus;

    @Column("nombre")
    private String name;

    @Column("descripcion")
    private String description;
}
