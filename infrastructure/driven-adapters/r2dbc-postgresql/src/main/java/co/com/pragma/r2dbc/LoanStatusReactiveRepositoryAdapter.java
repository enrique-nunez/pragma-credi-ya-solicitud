package co.com.pragma.r2dbc;

import co.com.pragma.model.loanapplication.LoanApplication;
import co.com.pragma.model.loanapplication.dto.LoanApplicationPagedResponse;
import co.com.pragma.model.loanapplication.dto.SearchRequest;
import co.com.pragma.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.pragma.model.loanstatus.Loanstatus;
import co.com.pragma.model.loanstatus.gateways.LoanstatusRepository;
import co.com.pragma.r2dbc.entity.LoanApplicationEntity;
import co.com.pragma.r2dbc.entity.LoanStatusEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import co.com.pragma.r2dbc.mappers.LoanApplicationSearchMapper;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
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
}
