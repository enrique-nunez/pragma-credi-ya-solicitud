package co.com.pragma.r2dbc;

import co.com.pragma.r2dbc.dto.LoanApplicationFieldsPageDto;
import co.com.pragma.r2dbc.entity.LoanApplicationEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// TODO: This file is just an example, you should delete or modify it
public interface LoanApplicationReactiveRepository extends ReactiveCrudRepository<LoanApplicationEntity, Long>, ReactiveQueryByExampleExecutor<LoanApplicationEntity> {

    @Query("""
            SELECT sol.id_solicitud AS idSolicitud,
               sol.monto AS montoSolicitado,
               sol.plazo AS plazoMeses,
               sol.email AS emailUsuario,
               tp.nombre AS tipoPrestamo,
               tp.tasa_interes AS tasaInteres,
               est.descripcion AS estadoSolicitud
        FROM solicitud sol
                 JOIN estados est ON sol.id_estado = est.id_estado
                 JOIN tipo_prestamo tp ON sol.id_tipo_prestamo = tp.id_tipo_prestamo
        WHERE est.nombre = 'PENDING'
        LIMIT :limit OFFSET :offset
        """)
    Flux<LoanApplicationFieldsPageDto> findPendingSummariesPaged(@Param("limit") int limit, @Param("offset") int offset);

    @Query("""
    SELECT COUNT(*) FROM solicitud sol
    JOIN estados est ON sol.id_estado = est.id_estado
    JOIN tipo_prestamo tp ON sol.id_tipo_prestamo = tp.id_tipo_prestamo
    WHERE est.nombre = 'PENDING'
    """)
    Mono<Long> countPendingSummaries();
}
