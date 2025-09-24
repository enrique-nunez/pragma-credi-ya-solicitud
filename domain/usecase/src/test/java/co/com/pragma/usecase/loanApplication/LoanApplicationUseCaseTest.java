package co.com.pragma.usecase.loanApplication;

import co.com.pragma.model.common.exceptions.InvalidInputException;
import co.com.pragma.model.common.exceptions.NotFoundException;
import co.com.pragma.model.common.models.PaginationResponse;
import co.com.pragma.model.loanType.LoanType;
import co.com.pragma.model.loanType.gateways.LoanTypeRepository;
import co.com.pragma.model.loanapplication.LoanApplication;
import co.com.pragma.model.loanapplication.dto.LoanApplicationPagedResponse;
import co.com.pragma.model.loanapplication.dto.SQSMessage;
import co.com.pragma.model.loanapplication.dto.SearchRequest;
import co.com.pragma.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.pragma.model.loanapplication.gateways.NotificationQueueGateway;
import co.com.pragma.model.loanstatus.gateways.LoanstatusRepository;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanApplicationUseCaseTest {
    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @Mock
    private LoanTypeRepository loanTypeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoanstatusRepository loanStatusRepository;

    @Mock
    private NotificationQueueGateway notificationQueueGateway;

    private LoanApplicationUseCase loanApplicationUseCase;

    private LoanApplication testLoanApplication;
    private LoanType testLoanType;

    @BeforeEach
    void setUp() {
        loanApplicationUseCase = new LoanApplicationUseCase(
                loanApplicationRepository,
                loanTypeRepository,
                userRepository,
                loanStatusRepository,
                notificationQueueGateway
        );

        testLoanType = LoanType.builder()
                .loanTypeId(1L)
                .name("Personal Loan")
                .build();

        testLoanApplication = LoanApplication.builder()
                .idLoan(1L)
                .email("test@example.com")
                .amount(new BigDecimal("10000"))
                .term(12)
                .loanTypeId(1L)
                .build();
    }

    @Test
    void saveLoanApplication_ValidApplication_ShouldSaveSuccessfully() {
        // Given
        when(loanTypeRepository.findById(anyLong())).thenReturn(Mono.just(testLoanType));
        when(userRepository.existUserByEmail(anyString())).thenReturn(Mono.just(true));
        when(loanApplicationRepository.save(any(LoanApplication.class))).thenReturn(Mono.just(testLoanApplication));

        // When & Then
        StepVerifier.create(loanApplicationUseCase.saveLoanApplication(testLoanApplication))
                .expectNext(testLoanApplication)
                .verifyComplete();
    }

    @Test
    void saveLoanApplication_LoanTypeNotExists_ShouldThrowException() {
        // Given
        when(loanTypeRepository.findById(anyLong())).thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(loanApplicationUseCase.saveLoanApplication(testLoanApplication))
                .expectError(NotFoundException.class)
                .verify();
    }

    @Test
    void saveLoanApplication_UserEmailNotExists_ShouldThrowException() {
        // Given
        when(loanTypeRepository.findById(anyLong())).thenReturn(Mono.just(testLoanType));
        when(userRepository.existUserByEmail(anyString())).thenReturn(Mono.just(false));

        // When & Then
        StepVerifier.create(loanApplicationUseCase.saveLoanApplication(testLoanApplication))
                .expectError(NotFoundException.class)
                .verify();
    }

    @Test
    void saveLoanApplication_NullEmail_ShouldThrowException() {
        // Given
        LoanApplication invalidApplication = testLoanApplication.toBuilder()
                .email(null)
                .build();

        // When & Then
        StepVerifier.create(loanApplicationUseCase.saveLoanApplication(invalidApplication))
                .expectError(InvalidInputException.class)
                .verify();
    }

    @Test
    void saveLoanApplication_BlankEmail_ShouldThrowException() {
        // Given
        LoanApplication invalidApplication = testLoanApplication.toBuilder()
                .email("   ")
                .build();

        // When & Then
        StepVerifier.create(loanApplicationUseCase.saveLoanApplication(invalidApplication))
                .expectError(InvalidInputException.class)
                .verify();
    }

    @Test
    void saveLoanApplication_RepositoryError_ShouldPropagateError() {
        // Given
        RuntimeException repositoryError = new RuntimeException("Database error");
        when(loanTypeRepository.findById(anyLong())).thenReturn(Mono.just(testLoanType));
        when(userRepository.existUserByEmail(anyString())).thenReturn(Mono.just(true));
        when(loanApplicationRepository.save(any(LoanApplication.class))).thenReturn(Mono.error(repositoryError));

        // When & Then
        StepVerifier.create(loanApplicationUseCase.saveLoanApplication(testLoanApplication))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void saveLoanApplication_LoanTypeRepositoryError_ShouldPropagateError() {
        // Given
        RuntimeException repositoryError = new RuntimeException("Database error");
        when(loanTypeRepository.findById(anyLong())).thenReturn(Mono.error(repositoryError));

        // When & Then
        StepVerifier.create(loanApplicationUseCase.saveLoanApplication(testLoanApplication))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void validateLoanApplication_InvalidAmount_ShouldThrowException() {
        LoanApplication invalidApplication = testLoanApplication.toBuilder()
                .amount(BigDecimal.ZERO)
                .build();

        StepVerifier.create(loanApplicationUseCase.validateLoanApplication(invalidApplication))
                .expectError(InvalidInputException.class)
                .verify();
    }

    @Test
    void validateLoanApplication_InvalidTerm_ShouldThrowException() {
        LoanApplication invalidApplication = testLoanApplication.toBuilder()
                .term(0)
                .build();

        StepVerifier.create(loanApplicationUseCase.validateLoanApplication(invalidApplication))
                .expectError(InvalidInputException.class)
                .verify();
    }

    @Test
    void validateLoanApplication_NullLoanTypeId_ShouldThrowException() {
        LoanApplication invalidApplication = testLoanApplication.toBuilder()
                .loanTypeId(null)
                .build();

        StepVerifier.create(loanApplicationUseCase.validateLoanApplication(invalidApplication))
                .expectError(InvalidInputException.class)
                .verify();
    }

    @Test
    void validateLoanApplication_InvalidEmailFormat_ShouldThrowException() {
        LoanApplication invalidApplication = testLoanApplication.toBuilder()
                .email("testexample.com") // sin @
                .build();

        StepVerifier.create(loanApplicationUseCase.validateLoanApplication(invalidApplication))
                .expectError(InvalidInputException.class)
                .verify();
    }

    @Test
    void validateLoanApplication_NullAmount_ShouldThrowException() {
        LoanApplication invalidApplication = testLoanApplication.toBuilder()
                .amount(null)
                .build();

        StepVerifier.create(loanApplicationUseCase.validateLoanApplication(invalidApplication))
                .expectError(InvalidInputException.class)
                .verify();
    }

    @Test
    void validateLoanApplication_NullTerm_ShouldThrowException() {
        LoanApplication invalidApplication = testLoanApplication.toBuilder()
                .term(null)
                .build();

        StepVerifier.create(loanApplicationUseCase.validateLoanApplication(invalidApplication))
                .expectError(InvalidInputException.class)
                .verify();
    }

    @Test
    void updateStatusLoanApplication_Approved_ShouldSendNotification() {
        Long loanApplicationId = 1L;
        String statusName = "APPROVED";
        Long statusId = 2L;
        LoanApplicationPagedResponse updatedApplication = LoanApplicationPagedResponse.builder()
                .emailUsuario("test@example.com")
                .estadoSolicitud("APPROVED")
                .build();

        when(loanStatusRepository.findIdByName(statusName)).thenReturn(Mono.just(statusId));
        when(loanApplicationRepository.findByLoanApplicationId(loanApplicationId)).thenReturn(Mono.just(updatedApplication));
        when(loanApplicationRepository.updateLoanApplication(loanApplicationId, statusId)).thenReturn(Mono.just(updatedApplication));
        when(notificationQueueGateway.publishLoanApplicationStatusChanged(any())).thenReturn(Mono.empty());

        StepVerifier.create(loanApplicationUseCase.updateStatusLoanApplication(loanApplicationId, statusName))
                .expectNext(updatedApplication)
                .verifyComplete();
    }

    @Test
    void updateStatusLoanApplication_OtherStatus_ShouldNotSendNotification() {
        Long loanApplicationId = 1L;
        String statusName = "PENDING";
        Long statusId = 3L;
        LoanApplicationPagedResponse updatedApplication = LoanApplicationPagedResponse.builder()
                .emailUsuario("test@example.com")
                .estadoSolicitud("PENDING")
                .build();

        when(loanStatusRepository.findIdByName(statusName)).thenReturn(Mono.just(statusId));
        when(loanApplicationRepository.findByLoanApplicationId(loanApplicationId)).thenReturn(Mono.just(updatedApplication));
        when(loanApplicationRepository.updateLoanApplication(loanApplicationId, statusId)).thenReturn(Mono.just(updatedApplication));

        StepVerifier.create(loanApplicationUseCase.updateStatusLoanApplication(loanApplicationId, statusName))
                .expectNext(updatedApplication)
                .verifyComplete();
    }

    @Test
    void updateStatusLoanApplication_ShouldPropagateError_WhenRepositoryFails() {
        Long loanApplicationId = 1L;
        String statusName = "APPROVED";
        RuntimeException error = new RuntimeException("Repository error");

        when(loanStatusRepository.findIdByName(statusName)).thenReturn(Mono.error(error));

        StepVerifier.create(loanApplicationUseCase.updateStatusLoanApplication(loanApplicationId, statusName))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void updateStatusLoanApplication_Approved_ShouldPropagateError_WhenNotificationFails() {
        Long loanApplicationId = 1L;
        String statusName = "APPROVED";
        Long statusId = 2L;
        LoanApplicationPagedResponse updatedApplication = LoanApplicationPagedResponse.builder()
                .emailUsuario("test@example.com")
                .estadoSolicitud("APPROVED")
                .build();

        when(loanStatusRepository.findIdByName(statusName)).thenReturn(Mono.just(statusId));
        when(loanApplicationRepository.findByLoanApplicationId(loanApplicationId)).thenReturn(Mono.just(updatedApplication));
        when(loanApplicationRepository.updateLoanApplication(loanApplicationId, statusId)).thenReturn(Mono.just(updatedApplication));
        when(notificationQueueGateway.publishLoanApplicationStatusChanged(any())).thenReturn(Mono.error(new RuntimeException("Notification error")));

        StepVerifier.create(loanApplicationUseCase.updateStatusLoanApplication(loanApplicationId, statusName))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void updateStatusLoanApplication_Rejected_ShouldSendNotification() {
        Long loanApplicationId = 1L;
        String statusName = "REJECTED";
        Long statusId = 4L;
        LoanApplicationPagedResponse updatedApplication = LoanApplicationPagedResponse.builder()
                .emailUsuario("test@example.com")
                .estadoSolicitud("REJECTED")
                .build();

        when(loanStatusRepository.findIdByName(statusName)).thenReturn(Mono.just(statusId));
        when(loanApplicationRepository.findByLoanApplicationId(loanApplicationId)).thenReturn(Mono.just(updatedApplication));
        when(loanApplicationRepository.updateLoanApplication(loanApplicationId, statusId)).thenReturn(Mono.just(updatedApplication));
        when(notificationQueueGateway.publishLoanApplicationStatusChanged(any())).thenReturn(Mono.empty());

        StepVerifier.create(loanApplicationUseCase.updateStatusLoanApplication(loanApplicationId, statusName))
                .expectNext(updatedApplication)
                .verifyComplete();
    }

    @Test
    void getPendingLoanApplicationsPaged_ShouldReturnPaginatedResponse() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setPage(0);
        searchRequest.setSize(5);
        LoanApplicationPagedResponse response = LoanApplicationPagedResponse.builder()
                .emailUsuario("test@example.com")
                .build();
        List<User> users = List.of(User.builder().email("test@example.com").build());

        when(loanApplicationRepository.findAllSummariesPaged(searchRequest)).thenReturn(Flux.just(response));
        when(loanApplicationRepository.countPendingSummaries()).thenReturn(Mono.just(1L));
        when(userRepository.getAllUsers()).thenReturn(Mono.just(users));

        StepVerifier.create(loanApplicationUseCase.getPendingLoanApplicationsPaged(searchRequest))
                .expectNextMatches(baseResponse -> {
                    PaginationResponse pagination = (PaginationResponse) baseResponse.getPagination();
                    return baseResponse.getData().size() == 1 &&
                            pagination.getTotalElements() == 1L;
                })
                .verifyComplete();
    }

    // Test para createSQSMessage
    @Test
    void createSQSMessage_ShouldBuildCorrectMessage() {
        LoanApplicationPagedResponse response = LoanApplicationPagedResponse.builder()
                .emailUsuario("test@example.com")
                .estadoSolicitud("APPROVED")
                .tipoPrestamo("Personal")
                .montoSolicitado(new BigDecimal("10000"))
                .plazoMeses(12)
                .tasaInteres(new BigDecimal("12.5"))
                .deudaTotalMensual(new BigDecimal("610.26"))
                .build();

        SQSMessage message = loanApplicationUseCase.createSQSMessage(response, 1L);

        assertEquals("test@example.com", message.to());
        assertTrue(message.body().contains("Banco CrediYa"));
    }

}
