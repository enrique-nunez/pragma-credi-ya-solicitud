package co.com.pragma.model.loanType.gateways;

import co.com.pragma.model.loanType.LoanType;
import reactor.core.publisher.Mono;

public interface LoanTypeRepository {

    Mono<LoanType> findById(Long id);

}
