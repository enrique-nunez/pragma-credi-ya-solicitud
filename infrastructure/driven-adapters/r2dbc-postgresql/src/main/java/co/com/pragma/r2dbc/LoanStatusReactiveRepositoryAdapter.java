package co.com.pragma.r2dbc;

import co.com.pragma.model.loanstatus.Loanstatus;
import co.com.pragma.model.loanstatus.gateways.LoanstatusRepository;
import co.com.pragma.r2dbc.entity.LoanStatusEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public class LoanStatusReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Loanstatus,
        LoanStatusEntity,
        Long,
        LoanStatusReactiveRepository> implements LoanstatusRepository {

    public LoanStatusReactiveRepositoryAdapter(LoanStatusReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Loanstatus.class));
    }

    @Override
    public Mono<Boolean> existsById(String status) {
        return repository.existsById(status);
    }

    @Override
    public Mono<Long> findIdByName(String statusName) {
        return repository.findByName(statusName)
                .map(LoanStatusEntity::getIdStatus);
    }
}
