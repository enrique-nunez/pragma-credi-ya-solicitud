package co.com.pragma.r2dbc;

import co.com.pragma.model.loanapplication.LoanApplication;
import co.com.pragma.model.loanapplication.dto.LoanApplicationPagedResponse;
import co.com.pragma.model.loanapplication.dto.SearchRequest;
import co.com.pragma.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.pragma.model.loanstatus.gateways.LoanstatusRepository;
import co.com.pragma.r2dbc.dto.LoanApplicationFieldsPageDto;
import co.com.pragma.r2dbc.entity.LoanApplicationEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import co.com.pragma.r2dbc.mappers.LoanApplicationSearchMapper;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public class LoanApplicationReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        LoanApplication,
        LoanApplicationEntity,
        Long,
        LoanApplicationReactiveRepository > implements LoanApplicationRepository {

    @Autowired
    private LoanApplicationSearchMapper mapper;

    public LoanApplicationReactiveRepositoryAdapter(LoanApplicationReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, LoanApplication.class));
    }

    @Override
    @Transactional
    public Mono<LoanApplication> save(LoanApplication loanApplication) {
        return super.save(loanApplication);
    }

    public Flux<LoanApplicationPagedResponse> findAllSummariesPaged(SearchRequest searchRequest) {
        int offset = searchRequest.getPage() * searchRequest.getSize();
        return repository.findPendingSummariesPaged(searchRequest.getStatusId(), searchRequest.getSize(), offset)
                .doOnNext(dto -> System.out.println("DTO desde BD: " + dto))
                .map(mapper::toModel)
                .doOnNext(response -> System.out.println("Response mapeada: " + response));
    }

    public Mono<Long> countPendingSummaries() {
        return repository.countPendingSummaries();
    }

    @Override
    public Mono<LoanApplicationPagedResponse> findByLoanApplicationId(Long loanApplicationId) {
        return repository.findByLoanApplicationId(loanApplicationId)
                .doOnNext(dto -> System.out.println("DTO desde BD: " + dto))
                .map(mapper::toModel)
                .doOnNext(response -> System.out.println("Response mapeada: " + response));
    }

    @Override
    public Mono<LoanApplicationPagedResponse> updateLoanApplication(Long loanApplicationId, Long loanStatusId) {
        return repository.updateStatusLoanApplication(loanApplicationId, loanStatusId)
                        .then(repository.findByLoanApplicationId(loanApplicationId))
                        .map(mapper::toModel);
    }
}
