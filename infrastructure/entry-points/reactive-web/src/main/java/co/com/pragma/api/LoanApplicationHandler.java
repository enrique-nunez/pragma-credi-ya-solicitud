package co.com.pragma.api;

import co.com.pragma.api.dto.LoanApplicationResponseDto;
import co.com.pragma.api.dto.SaveLoanApplicationDto;
import co.com.pragma.api.dto.StatusRequestDto;
import co.com.pragma.api.mapper.LoanApplicationMapper;
import co.com.pragma.model.common.models.BaseResponse;
import co.com.pragma.model.common.models.GlobalBusinessValidation;
import co.com.pragma.model.common.models.ResponseMessages;
import co.com.pragma.model.loanapplication.dto.LoanApplicationPagedResponse;
import co.com.pragma.model.loanapplication.dto.SearchRequest;
import co.com.pragma.usecase.loanApplication.LoanApplicationUseCase;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setPage(serverRequest.queryParam("page").map(Integer::parseInt).orElse(0));
        searchRequest.setSize(serverRequest.queryParam("size").map(Integer::parseInt).orElse(10));
        searchRequest.setStatusId(serverRequest.queryParam("statusId").map(Integer::parseInt).orElse(1));

        return loanapplicationUseCase.getPendingLoanApplicationsPaged(searchRequest)
                .flatMap(response -> ServerResponse.ok().bodyValue(response))
                .doOnError(e -> log.error("Error retrieving loan applications: {}", e.getMessage()))
                .onErrorResume(Mono::error);
    }

    public Mono<ServerResponse> updateStatusLoanApplication(ServerRequest serverRequest) {
        Long loanApplicationId = Long.valueOf(serverRequest.pathVariable("id"));
        return serverRequest.bodyToMono(StatusRequestDto.class)
                .doOnSubscribe(subscription -> log.debug(">> PUT /api/v1/solicitud/{id} - start"))
                .flatMap(dto -> {
                    Set<ConstraintViolation<StatusRequestDto>> violations = validator.validate(dto);
                    return violations.isEmpty() ? Mono.just(dto) : Mono.error(new ConstraintViolationException(violations));
                })
                .flatMap(statusRequestDto ->
                        loanapplicationUseCase.updateStatusLoanApplication(loanApplicationId, statusRequestDto.status())
                )
                .map(responseDto -> {
                    BaseResponse<LoanApplicationPagedResponse> response = new BaseResponse<>(
                            true,
                            responseDto,
                            ResponseMessages.OPERATION_SUCCESSFUL
                    );
                    response.setStateCode(HttpStatus.OK.value());
                    return response;
                })
                .doOnSuccess(success -> log.info("Loan updated in the database"))
                .doOnError(error -> log.error("Loan update failed: {}", error.getMessage()))
                .flatMap(updatedLoanApplication -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updatedLoanApplication))
                .onErrorResume(Mono::error)
                .doFinally(signalType -> log.debug("<< PUT /api/v1/loans/{id} - end"));
    }
}
