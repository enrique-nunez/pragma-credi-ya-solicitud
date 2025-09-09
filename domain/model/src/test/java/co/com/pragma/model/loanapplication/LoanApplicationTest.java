package co.com.pragma.model.loanapplication;

import co.com.pragma.model.common.enums.TypeStatusLoan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class LoanApplicationTest {

    private LoanApplication testLoanApplication;

    @BeforeEach
    void setUp() {
        testLoanApplication = LoanApplication.builder()
                .idLoan(1L)
                .amount(new BigDecimal("10000"))
                .term(12)
                .email("test@example.com")
                .idStatus(1L)
                .loanTypeId(2L)
                .build();
    }

    @Test
    void getIdLoan_ShouldReturnCorrectId() {
        assertEquals(1L, testLoanApplication.getIdLoan());
    }

    @Test
    void getAmount_ShouldReturnCorrectAmount() {
        assertEquals(new BigDecimal("10000"), testLoanApplication.getAmount());
    }

    @Test
    void getTerm_ShouldReturnCorrectTerm() {
        assertEquals(12, testLoanApplication.getTerm());
    }

    @Test
    void getEmail_ShouldReturnCorrectEmail() {
        assertEquals("test@example.com", testLoanApplication.getEmail());
    }

    @Test
    void getIdStatus_ShouldReturnCorrectStatus() {
        assertEquals(1L, testLoanApplication.getIdStatus());
    }

    @Test
    void getLoanTypeId_ShouldReturnCorrectLoanTypeId() {
        assertEquals(2L, testLoanApplication.getLoanTypeId());
    }

    @Test
    void toBuilder_ShouldCreateBuilderWithSameValues() {
        LoanApplication copiedApplication = testLoanApplication.toBuilder().build();

        assertEquals(testLoanApplication.getIdLoan(), copiedApplication.getIdLoan());
        assertEquals(testLoanApplication.getAmount(), copiedApplication.getAmount());
        assertEquals(testLoanApplication.getTerm(), copiedApplication.getTerm());
        assertEquals(testLoanApplication.getEmail(), copiedApplication.getEmail());
        assertEquals(testLoanApplication.getIdStatus(), copiedApplication.getIdStatus());
        assertEquals(testLoanApplication.getLoanTypeId(), copiedApplication.getLoanTypeId());
    }

    @Test
    void toBuilder_ModifyField_ShouldCreateNewApplicationWithModification() {
        LoanApplication modifiedApplication = testLoanApplication.toBuilder()
                .amount(new BigDecimal("20000"))
                .build();

        assertEquals(new BigDecimal("20000"), modifiedApplication.getAmount());
        assertEquals(testLoanApplication.getTerm(), modifiedApplication.getTerm());
        assertEquals(testLoanApplication.getEmail(), modifiedApplication.getEmail());
        assertEquals(testLoanApplication.getIdLoan(), modifiedApplication.getIdLoan());
    }

    @Test
    void defaultStatusLoan_ShouldSetPendingStatus() {
        LoanApplication application = LoanApplication.builder()
                .idLoan(1L)
                .amount(new BigDecimal("5000"))
                .term(6)
                .email("pending@example.com")
                .loanTypeId(1L)
                .build();

        LoanApplication result = application.defaultStatusLoan();

        assertEquals(TypeStatusLoan.PENDING.getValue(), result.getIdStatus());
        assertSame(application, result); // Should return the same instance
    }

    @Test
    void defaultStatusLoan_WithExistingStatus_ShouldOverrideWithPending() {
        LoanApplication application = LoanApplication.builder()
                .idLoan(1L)
                .amount(new BigDecimal("5000"))
                .term(6)
                .email("override@example.com")
                .idStatus(999L) // Some other status
                .loanTypeId(1L)
                .build();

        LoanApplication result = application.defaultStatusLoan();

        assertEquals(TypeStatusLoan.PENDING.getValue(), result.getIdStatus());
    }

    @Test
    void builder_WithAllFields_ShouldCreateCompleteApplication() {
        LoanApplication application = LoanApplication.builder()
                .idLoan(5L)
                .amount(new BigDecimal("25000"))
                .term(24)
                .email("complete@example.com")
                .idStatus(2L)
                .loanTypeId(3L)
                .build();

        assertEquals(5L, application.getIdLoan());
        assertEquals(new BigDecimal("25000"), application.getAmount());
        assertEquals(24, application.getTerm());
        assertEquals("complete@example.com", application.getEmail());
        assertEquals(2L, application.getIdStatus());
        assertEquals(3L, application.getLoanTypeId());
    }

    @Test
    void builder_WithPartialFields_ShouldCreateApplicationWithNulls() {
        LoanApplication application = LoanApplication.builder()
                .email("partial@example.com")
                .amount(new BigDecimal("1000"))
                .build();

        assertNull(application.getIdLoan());
        assertEquals(new BigDecimal("1000"), application.getAmount());
        assertNull(application.getTerm());
        assertEquals("partial@example.com", application.getEmail());
        assertNull(application.getIdStatus());
        assertNull(application.getLoanTypeId());
    }

    @Test
    void constructor_WithAllParameters_ShouldSetAllFields() {
        LoanApplication application = new LoanApplication(
                10L,
                new BigDecimal("15000"),
                18,
                "constructor@example.com",
                3L,
                4L
        );

        assertEquals(10L, application.getIdLoan());
        assertEquals(new BigDecimal("15000"), application.getAmount());
        assertEquals(18, application.getTerm());
        assertEquals("constructor@example.com", application.getEmail());
        assertEquals(3L, application.getIdStatus());
        assertEquals(4L, application.getLoanTypeId());
    }

    @Test
    void constructor_Default_ShouldCreateEmptyApplication() {
        LoanApplication application = new LoanApplication();

        assertNull(application.getIdLoan());
        assertNull(application.getAmount());
        assertNull(application.getTerm());
        assertNull(application.getEmail());
        assertNull(application.getIdStatus());
        assertNull(application.getLoanTypeId());
    }

    @Test
    void setters_ShouldUpdateFields() {
        LoanApplication application = new LoanApplication();

        application.setIdLoan(100L);
        application.setAmount(new BigDecimal("50000"));
        application.setTerm(36);
        application.setEmail("setter@example.com");
        application.setIdStatus(5L);
        application.setLoanTypeId(6L);

        assertEquals(100L, application.getIdLoan());
        assertEquals(new BigDecimal("50000"), application.getAmount());
        assertEquals(36, application.getTerm());
        assertEquals("setter@example.com", application.getEmail());
        assertEquals(5L, application.getIdStatus());
        assertEquals(6L, application.getLoanTypeId());
    }

    @Test
    void setters_WithNullValues_ShouldAcceptNulls() {
        testLoanApplication.setIdLoan(null);
        testLoanApplication.setAmount(null);
        testLoanApplication.setTerm(null);
        testLoanApplication.setEmail(null);
        testLoanApplication.setIdStatus(null);
        testLoanApplication.setLoanTypeId(null);

        assertNull(testLoanApplication.getIdLoan());
        assertNull(testLoanApplication.getAmount());
        assertNull(testLoanApplication.getTerm());
        assertNull(testLoanApplication.getEmail());
        assertNull(testLoanApplication.getIdStatus());
        assertNull(testLoanApplication.getLoanTypeId());
    }

    @Test
    void builder_ChainedCalls_ShouldWorkCorrectly() {
        LoanApplication application = LoanApplication.builder()
                .idLoan(1L)
                .amount(new BigDecimal("7500"))
                .term(9)
                .email("chained@example.com")
                .idStatus(1L)
                .loanTypeId(2L)
                .build()
                .defaultStatusLoan();

        assertEquals(1L, application.getIdLoan());
        assertEquals(new BigDecimal("7500"), application.getAmount());
        assertEquals(9, application.getTerm());
        assertEquals("chained@example.com", application.getEmail());
        assertEquals(TypeStatusLoan.PENDING.getValue(), application.getIdStatus());
        assertEquals(2L, application.getLoanTypeId());
    }

    @Test
    void toBuilder_MultipleModifications_ShouldCreateNewApplicationWithAllChanges() {
        LoanApplication modifiedApplication = testLoanApplication.toBuilder()
                .amount(new BigDecimal("30000"))
                .term(48)
                .email("modified@example.com")
                .build();

        assertEquals(new BigDecimal("30000"), modifiedApplication.getAmount());
        assertEquals(48, modifiedApplication.getTerm());
        assertEquals("modified@example.com", modifiedApplication.getEmail());

        // Original values should remain unchanged
        assertEquals(testLoanApplication.getIdLoan(), modifiedApplication.getIdLoan());
        assertEquals(testLoanApplication.getIdStatus(), modifiedApplication.getIdStatus());
        assertEquals(testLoanApplication.getLoanTypeId(), modifiedApplication.getLoanTypeId());
    }

    @Test
    void builder_WithZeroValues_ShouldAcceptZeroValues() {
        LoanApplication application = LoanApplication.builder()
                .idLoan(0L)
                .amount(BigDecimal.ZERO)
                .term(0)
                .loanTypeId(0L)
                .idStatus(0L)
                .build();

        assertEquals(0L, application.getIdLoan());
        assertEquals(BigDecimal.ZERO, application.getAmount());
        assertEquals(0, application.getTerm());
        assertEquals(0L, application.getLoanTypeId());
        assertEquals(0L, application.getIdStatus());
    }

    @Test
    void builder_WithNegativeValues_ShouldAcceptNegativeValues() {
        LoanApplication application = LoanApplication.builder()
                .amount(new BigDecimal("-1000"))
                .term(-5)
                .build();

        assertEquals(new BigDecimal("-1000"), application.getAmount());
        assertEquals(-5, application.getTerm());
    }

    @Test
    void builder_WithEmptyEmail_ShouldAcceptEmptyString() {
        LoanApplication application = LoanApplication.builder()
                .email("")
                .build();

        assertEquals("", application.getEmail());
    }
}