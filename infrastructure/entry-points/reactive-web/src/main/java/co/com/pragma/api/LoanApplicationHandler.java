package co.com.pragma.api;

import co.com.pragma.api.dto.LoanApplicationResponseDto;
import co.com.pragma.api.dto.SaveLoanApplicationDto;
import co.com.pragma.api.mapper.LoanApplicationMapper;
import co.com.pragma.model.common.exceptions.GlobalBusinessException;
import co.com.pragma.model.common.exceptions.InvalidInputException;
import co.com.pragma.model.common.models.BaseResponse;
import co.com.pragma.model.common.models.GlobalBusinessValidation;
import co.com.pragma.model.common.models.ResponseMessages;
import co.com.pragma.model.loanapplication.dto.SearchRequest;
import co.com.pragma.usecase.loanApplication.LoanApplicationUseCase;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoanApplicationHandler {

    private final LoanApplicationUseCase loanapplicationUseCase;
    private final LoanApplicationMapper loanApplicationMapper;
    private final Validator validator;

    public Mono<ServerResponse> saveLoanApplication(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(SaveLoanApplicationDto.class)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(GlobalBusinessValidation.INVALID_BODY_REQUEST)))
                .flatMap(dto -> {
                    Set<ConstraintViolation<SaveLoanApplicationDto>> violations = validator.validate(dto);
                    if (!violations.isEmpty()) {
                        return Mono.error(new ConstraintViolationException(violations));
                    }
                    return Mono.just(dto);
                })
                .map(loanApplicationMapper::toLoanApplication)
                .flatMap(loanapplicationUseCase::saveLoanApplication)
                .map(loanApplicationMapper::toLoanApplicationResponseDto)
                .flatMap(responseDto -> {
                    BaseResponse<LoanApplicationResponseDto> response = new BaseResponse<>(
                            true,
                            responseDto,
                            ResponseMessages.OPERATION_SUCCESSFUL
                    );
                    response.setStateCode(HttpStatus.CREATED.value());
                    return ServerResponse.status(HttpStatus.CREATED).bodyValue(response);
                })
                .onErrorResume(Mono::error);
    }

    public Mono<ServerResponse> getLoanApplicationsSearch(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(SearchRequest.class)
                .switchIfEmpty(Mono.just(new SearchRequest()))
                .flatMap(searchRequest -> loanapplicationUseCase.getPendingLoanApplicationsPaged(searchRequest)
                        .flatMap(response -> ServerResponse.ok().bodyValue(response))
                )
                .doOnError(e -> log.error("Error retrieving loan applications: {}", e.getMessage()))
                .onErrorResume(Mono::error);
    }
}
