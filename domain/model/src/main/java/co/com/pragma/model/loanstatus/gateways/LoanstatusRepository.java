package co.com.pragma.model.loanstatus.gateways;

import reactor.core.publisher.Mono;

public interface LoanstatusRepository {
    Mono<Boolean> existsById(String statusId);
    Mono<Long> findIdByName(String statusName);
}
