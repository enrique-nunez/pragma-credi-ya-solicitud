package co.com.pragma.r2dbc;

import co.com.pragma.model.loanapplication.LoanApplication;
import co.com.pragma.r2dbc.dto.LoanApplicationFieldsPageDto;
import co.com.pragma.r2dbc.entity.LoanApplicationEntity;
import co.com.pragma.r2dbc.entity.LoanStatusEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// TODO: This file is just an example, you should delete or modify it
public interface LoanStatusReactiveRepository extends ReactiveCrudRepository<LoanStatusEntity, Long>, ReactiveQueryByExampleExecutor<LoanStatusEntity> {

    @Query("SELECT EXISTS(SELECT 1 FROM estados WHERE nombre = $1)")
    Mono<Boolean> existsById(String status);
}
