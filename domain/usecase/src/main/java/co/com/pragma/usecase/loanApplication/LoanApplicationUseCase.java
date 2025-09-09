package co.com.pragma.usecase.loanApplication;

import co.com.pragma.model.common.exceptions.InvalidInputException;
import co.com.pragma.model.common.exceptions.NotFoundException;
import co.com.pragma.model.common.models.BaseResponse;
import co.com.pragma.model.common.models.PaginationResponse;
import co.com.pragma.model.common.models.ResponseMessages;
import co.com.pragma.model.loanType.gateways.LoanTypeRepository;
import co.com.pragma.model.loanapplication.LoanApplication;
import co.com.pragma.model.loanapplication.dto.LoanApplicationPagedResponse;
import co.com.pragma.model.loanapplication.dto.LoanApplicationSummaryView;
import co.com.pragma.model.loanapplication.dto.SearchRequest;
import co.com.pragma.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.model.common.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class LoanApplicationUseCase {

    private static final Logger logger = Logger.getLogger(LoanApplicationUseCase.class.getName());
    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final UserRepository userRepository;

    public Mono<LoanApplication> saveLoanApplication(LoanApplication loanApplication) {
        logger.info("Iniciando registro de solicitud de préstamo para email: " + loanApplication.getEmail());
        return validateLoanApplication(loanApplication)
                .map(LoanApplication::defaultStatusLoan)
                .flatMap(validatedApplication -> {
                    logger.fine("Validación exitosa para email: " + validatedApplication.getEmail());
                    return loanTypeRepository.findById(validatedApplication.getLoanTypeId())
                            .switchIfEmpty(Mono.error(new NotFoundException(ErrorCode.LOAN_TYPE_NOT_EXISTS)))
                            .flatMap(loanType -> {
                                logger.fine("Tipo de préstamo encontrado: " + loanType.getLoanTypeId());
                                return userRepository.existUserByEmail(validatedApplication.getEmail())
                                        .flatMap(exists -> exists
                                                ? loanApplicationRepository.save(validatedApplication)
                                                : Mono.error(new NotFoundException(ErrorCode.USER_EMAIL_NOT_EXISTS))
                                        );
                            });
                });
    }

    public Mono<BaseResponse<List<LoanApplicationPagedResponse>>> getPendingLoanApplicationsPaged(SearchRequest searchRequest) {
        return loanApplicationRepository.findAllSummariesPaged(searchRequest)
                .collectList()
                .zipWith(loanApplicationRepository.countPendingSummaries())
                .map(tuple -> {
                    List<LoanApplicationPagedResponse> content = tuple.getT1();
                    Long totalElements = tuple.getT2();

                    PaginationResponse pagination = PaginationResponse.builder()
                            .page(searchRequest.getPage())
                            .size(searchRequest.getSize())
                            .totalElements(totalElements)
                            .totalPages((int) Math.ceil((double) totalElements / searchRequest.getSize()))
                            .build();

                    BaseResponse<List<LoanApplicationPagedResponse>> response = new BaseResponse<>(
                            true, content, ResponseMessages.OPERATION_SUCCESSFUL
                    );
                    response.setPagination(pagination);
                    return response;
                });
    }



    Mono<LoanApplication> validateLoanApplication(LoanApplication loanApplication) {
        if (loanApplication.getEmail() == null || loanApplication.getEmail().isBlank()) {
            logger.warning("Validación fallida: " + ErrorCode.TYPE_REQUIRED.getMessage());
            return Mono.error(new InvalidInputException(ErrorCode.EMAIL_REQUIRED));
        }
        if (!loanApplication.getEmail().contains("@")) {
            logger.warning("Validación fallida: " + ErrorCode.TYPE_REQUIRED.getMessage());
            return Mono.error(new InvalidInputException(ErrorCode.INVALID_EMAIL_FORMAT));
        }
        if (loanApplication.getAmount() == null) {
            logger.warning("Validación fallida: " + ErrorCode.TYPE_REQUIRED.getMessage());
            return Mono.error(new InvalidInputException(ErrorCode.AMOUNT_REQUIRED));
        }
        if (loanApplication.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            logger.warning("Validación fallida:" + ErrorCode.TYPE_REQUIRED.getMessage());
            return Mono.error(new InvalidInputException(ErrorCode.INVALID_AMOUNT));
        }
        if (loanApplication.getTerm() == null) {
            logger.warning("Validación fallida: " + ErrorCode.TYPE_REQUIRED.getMessage());
            return Mono.error(new InvalidInputException(ErrorCode.TERM_REQUIRED));
        }
        if (loanApplication.getTerm() <= 0) {
            logger.warning("Validación fallida:" + ErrorCode.TYPE_REQUIRED.getMessage());
            return Mono.error(new InvalidInputException(ErrorCode.INVALID_TERM));
        }
        if (loanApplication.getLoanTypeId() == null) {
            logger.warning("Validación fallida: " + ErrorCode.TYPE_REQUIRED.getMessage());
            return Mono.error(new InvalidInputException(ErrorCode.TYPE_REQUIRED));
        }
        logger.fine("Validación exitosa para email: " + loanApplication.getEmail());
        return Mono.just(loanApplication);
    }

}
