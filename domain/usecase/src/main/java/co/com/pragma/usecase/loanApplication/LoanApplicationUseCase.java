package co.com.pragma.usecase.loanApplication;

import co.com.pragma.model.common.enums.TypeStatusLoan;
import co.com.pragma.model.common.exceptions.InvalidInputException;
import co.com.pragma.model.common.exceptions.NotFoundException;
import co.com.pragma.model.common.models.BaseResponse;
import co.com.pragma.model.common.models.PaginationResponse;
import co.com.pragma.model.common.models.ResponseMessages;
import co.com.pragma.model.loanType.gateways.LoanTypeRepository;
import co.com.pragma.model.loanapplication.LoanApplication;
import co.com.pragma.model.loanapplication.dto.LoanApplicationPagedResponse;
import co.com.pragma.model.loanapplication.dto.LoanApplicationPagedResponseMapper;
import co.com.pragma.model.loanapplication.dto.SQSMessage;
import co.com.pragma.model.loanapplication.dto.SearchRequest;
import co.com.pragma.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.pragma.model.loanapplication.gateways.NotificationQueueGateway;
import co.com.pragma.model.loanstatus.gateways.LoanstatusRepository;
import co.com.pragma.model.user.User;
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
    private final LoanstatusRepository loanStatusRepository;
    private final NotificationQueueGateway notificationQueueGateway;

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
        return Mono.zip(
                    loanApplicationRepository.findAllSummariesPaged(searchRequest).collectList(),
                    loanApplicationRepository.countPendingSummaries(),
                    userRepository.getAllUsers()
                ).map(tuple -> {
                        List<User> users = tuple.getT3();
                        List<LoanApplicationPagedResponse> content = tuple.getT1().stream()
                            .map(summary -> LoanApplicationPagedResponseMapper.map(summary, users))
                            .toList();

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

    public Mono<LoanApplicationPagedResponse> updateStatusLoanApplication(Long loanApplicationId, String statusName) {
        return loanStatusRepository.findIdByName(statusName)
                .switchIfEmpty(Mono.error(new NotFoundException(ErrorCode.STATUS_LOAN_NOT_EXISTS)))
                .flatMap(loanStatusId -> loanApplicationRepository.findByLoanApplicationId(loanApplicationId)
                        .switchIfEmpty(Mono.error(new NotFoundException(ErrorCode.LOAN_TYPE_NOT_EXISTS)))
                        .flatMap(loanApplication -> loanApplicationRepository.updateLoanApplication(loanApplicationId, loanStatusId)
                                .switchIfEmpty(Mono.error(new NotFoundException(ErrorCode.STATUS_LOAN_NOT_EXISTS)))
                                .flatMap(updatedApplication -> {
                                    if (TypeStatusLoan.APPROVED.getDescription().equalsIgnoreCase(statusName) || TypeStatusLoan.REJECTED.getDescription().equalsIgnoreCase(statusName)) {
                                        return notificationQueueGateway.publishLoanApplicationStatusChanged(
                                                        createSQSMessage(updatedApplication, loanApplicationId)
                                                )
                                                .doOnError(error -> logger.warning("Error sending notification: " + error.getMessage()))
                                                .thenReturn(updatedApplication);
                                    }
                                    return Mono.just(updatedApplication);
                                })
                        )
                );
    }

    public SQSMessage createSQSMessage(LoanApplicationPagedResponse loanApplication, Long loanApplicationId) {
        return SQSMessage.builder()
                .to(loanApplication.getEmailUsuario())
                .subject("Su solicitud de prestamo ha sido " + loanApplication.getEstadoSolicitud().toLowerCase())
                .body(
                        "Estimado/a  usuario,\n\n" +
                                "Le informamos que su solicitud de prestamo  ha sido " + loanApplication.getEstadoSolicitud().toLowerCase() + ".\n\n" +
                                "Detalles de la solicitud:\n" +
                                "- Tipo de prestamo: " + loanApplication.getTipoPrestamo() + "\n" +
                                "- Monto solicitado: $" + loanApplication.getMontoSolicitado() + "\n" +
                                "- Plazo (meses): " + loanApplication.getPlazoMeses() + "\n" +
                                "- Tasa de interes: " + loanApplication.getTasaInteres() + "%\n" +
                                "- Deuda mensual: $" + loanApplication.getDeudaTotalMensual() + "\n\n" +
                                "Por favor, no responda a este correo. Si tiene dudas, comuníquese con nuestro equipo de atencion.\n\n" +
                                "Atentamente,\nBanco CrediYa"
                )
                .build();
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
