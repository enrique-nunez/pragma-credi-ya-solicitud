package co.com.pragma.model.loanapplication.gateways;

import co.com.pragma.model.loanapplication.dto.SQSMessage;
import reactor.core.publisher.Mono;

public interface NotificationQueueGateway {
    Mono<Void> publishLoanApplicationStatusChanged(SQSMessage sqsMessage);
}
