package co.com.pragma.usecase.loanApplication;

import co.com.pragma.model.common.exceptions.InvalidInputException;
import co.com.pragma.model.common.exceptions.NotFoundException;
import co.com.pragma.model.loanType.LoanType;
import co.com.pragma.model.loanType.gateways.LoanTypeRepository;
import co.com.pragma.model.loanapplication.LoanApplication;
import co.com.pragma.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.pragma.model.loanapplication.gateways.NotificationQueueGateway;
import co.com.pragma.model.loanstatus.gateways.LoanstatusRepository;
import co.com.pragma.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

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

//    @Test
//    void getPendingLoanApplicationsPaged_ShouldReturnResponseWithPagination() {
//        List<LoanApplicationSummaryView> summaries = List.of(/* mocks o instancias de prueba */);
//        when(loanApplicationRepository.findAllSummariesPaged(anyInt(), anyInt()))
//                .thenReturn(reactor.core.publisher.Flux.fromIterable(summaries));
//        when(loanApplicationRepository.countPendingSummaries())
//                .thenReturn(Mono.just(10L));
//
//        StepVerifier.create(loanApplicationUseCase.getPendingLoanApplicationsPaged(0, 5))
//                .expectNextMatches(response -> response.getPagination().get("totalElements").equals(10L))
//                .verifyComplete();
//    }

}
