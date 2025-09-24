package co.com.pragma.model.loanapplication.gateways;

import co.com.pragma.model.loanapplication.LoanApplication;
import co.com.pragma.model.loanapplication.dto.LoanApplicationPagedResponse;
import co.com.pragma.model.loanapplication.dto.SearchRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LoanApplicationRepository {
    Mono<LoanApplication> save(LoanApplication loanApplication);
    Mono<LoanApplicationPagedResponse> findByLoanApplicationId(Long loanApplicationId);
    Flux<LoanApplicationPagedResponse> findAllSummariesPaged(SearchRequest searchRequest);
    Mono<Long> countPendingSummaries();
    Mono<LoanApplicationPagedResponse> updateLoanApplication(Long loanApplicationId, Long loanStatusId);
}
