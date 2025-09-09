package co.com.pragma.model.loanType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class LoanTypeTest {

    private LoanType testLoanType;

    @BeforeEach
    void setUp() {
        testLoanType = LoanType.builder()
                .loanTypeId(1L)
                .name("Personal Loan")
                .maxAmount(new BigDecimal("50000"))
                .interestRate(new BigDecimal("15.5"))
                .automaticValidation(true)
                .build();
    }

    @Test
    void getLoanTypeId_ShouldReturnCorrectId() {
        assertEquals(1L, testLoanType.getLoanTypeId());
    }

    @Test
    void getName_ShouldReturnCorrectName() {
        assertEquals("Personal Loan", testLoanType.getName());
    }

    @Test
    void getMaxAmount_ShouldReturnCorrectMaxAmount() {
        assertEquals(new BigDecimal("50000"), testLoanType.getMaxAmount());
    }

    @Test
    void getInterestRate_ShouldReturnCorrectInterestRate() {
        assertEquals(new BigDecimal("15.5"), testLoanType.getInterestRate());
    }

    @Test
    void getAutomaticValidation_ShouldReturnCorrectAutomaticValidation() {
        assertTrue(testLoanType.getAutomaticValidation());
    }

    @Test
    void toBuilder_ShouldCreateBuilderWithSameValues() {
        LoanType copiedLoanType = testLoanType.toBuilder().build();

        assertEquals(testLoanType.getLoanTypeId(), copiedLoanType.getLoanTypeId());
        assertEquals(testLoanType.getName(), copiedLoanType.getName());
        assertEquals(testLoanType.getMaxAmount(), copiedLoanType.getMaxAmount());
        assertEquals(testLoanType.getInterestRate(), copiedLoanType.getInterestRate());
        assertEquals(testLoanType.getAutomaticValidation(), copiedLoanType.getAutomaticValidation());
    }

    @Test
    void toBuilder_ModifyField_ShouldCreateNewLoanTypeWithModification() {
        LoanType modifiedLoanType = testLoanType.toBuilder()
                .name("Modified Loan")
                .build();

        assertEquals("Modified Loan", modifiedLoanType.getName());
        assertEquals(testLoanType.getMaxAmount(), modifiedLoanType.getMaxAmount());
        assertEquals(testLoanType.getInterestRate(), modifiedLoanType.getInterestRate());
        assertEquals(testLoanType.getLoanTypeId(), modifiedLoanType.getLoanTypeId());
    }

    @Test
    void builder_WithAllFields_ShouldCreateCompleteLoanType() {
        LoanType loanType = LoanType.builder()
                .loanTypeId(5L)
                .name("Business Loan")
                .maxAmount(new BigDecimal("100000"))
                .interestRate(new BigDecimal("12.75"))
                .automaticValidation(false)
                .build();

        assertEquals(5L, loanType.getLoanTypeId());
        assertEquals("Business Loan", loanType.getName());
        assertEquals(new BigDecimal("100000"), loanType.getMaxAmount());
        assertEquals(new BigDecimal("12.75"), loanType.getInterestRate());
        assertFalse(loanType.getAutomaticValidation());
    }

    @Test
    void builder_WithPartialFields_ShouldCreateLoanTypeWithNulls() {
        LoanType loanType = LoanType.builder()
                .name("Partial Loan")
                .maxAmount(new BigDecimal("25000"))
                .build();

        assertNull(loanType.getLoanTypeId());
        assertEquals("Partial Loan", loanType.getName());
        assertEquals(new BigDecimal("25000"), loanType.getMaxAmount());
        assertNull(loanType.getInterestRate());
        assertNull(loanType.getAutomaticValidation());
    }

    @Test
    void constructor_WithAllParameters_ShouldSetAllFields() {
        LoanType loanType = new LoanType(
                10L,
                "Constructor Loan",
                new BigDecimal("75000"),
                new BigDecimal("18.25"),
                true
        );

        assertEquals(10L, loanType.getLoanTypeId());
        assertEquals("Constructor Loan", loanType.getName());
        assertEquals(new BigDecimal("75000"), loanType.getMaxAmount());
        assertEquals(new BigDecimal("18.25"), loanType.getInterestRate());
        assertTrue(loanType.getAutomaticValidation());
    }

    @Test
    void constructor_Default_ShouldCreateEmptyLoanType() {
        LoanType loanType = new LoanType();

        assertNull(loanType.getLoanTypeId());
        assertNull(loanType.getName());
        assertNull(loanType.getMaxAmount());
        assertNull(loanType.getInterestRate());
        assertNull(loanType.getAutomaticValidation());
    }

    @Test
    void setters_ShouldUpdateFields() {
        LoanType loanType = new LoanType();

        loanType.setLoanTypeId(100L);
        loanType.setName("Setter Loan");
        loanType.setMaxAmount(new BigDecimal("200000"));
        loanType.setInterestRate(new BigDecimal("8.5"));
        loanType.setAutomaticValidation(false);

        assertEquals(100L, loanType.getLoanTypeId());
        assertEquals("Setter Loan", loanType.getName());
        assertEquals(new BigDecimal("200000"), loanType.getMaxAmount());
        assertEquals(new BigDecimal("8.5"), loanType.getInterestRate());
        assertFalse(loanType.getAutomaticValidation());
    }

    @Test
    void setters_WithNullValues_ShouldAcceptNulls() {
        testLoanType.setLoanTypeId(null);
        testLoanType.setName(null);
        testLoanType.setMaxAmount(null);
        testLoanType.setInterestRate(null);
        testLoanType.setAutomaticValidation(null);

        assertNull(testLoanType.getLoanTypeId());
        assertNull(testLoanType.getName());
        assertNull(testLoanType.getMaxAmount());
        assertNull(testLoanType.getInterestRate());
        assertNull(testLoanType.getAutomaticValidation());
    }

    @Test
    void builder_ChainedCalls_ShouldWorkCorrectly() {
        LoanType loanType = LoanType.builder()
                .loanTypeId(1L)
                .name("Chained Loan")
                .maxAmount(new BigDecimal("30000"))
                .interestRate(new BigDecimal("14.0"))
                .automaticValidation(true)
                .build();

        assertEquals(1L, loanType.getLoanTypeId());
        assertEquals("Chained Loan", loanType.getName());
        assertEquals(new BigDecimal("30000"), loanType.getMaxAmount());
        assertEquals(new BigDecimal("14.0"), loanType.getInterestRate());
        assertTrue(loanType.getAutomaticValidation());
    }

    @Test
    void toBuilder_MultipleModifications_ShouldCreateNewLoanTypeWithAllChanges() {
        LoanType modifiedLoanType = testLoanType.toBuilder()
                .name("Modified Business Loan")
                .maxAmount(new BigDecimal("150000"))
                .interestRate(new BigDecimal("10.25"))
                .build();

        assertEquals("Modified Business Loan", modifiedLoanType.getName());
        assertEquals(new BigDecimal("150000"), modifiedLoanType.getMaxAmount());
        assertEquals(new BigDecimal("10.25"), modifiedLoanType.getInterestRate());

        // Original values should remain unchanged
        assertEquals(testLoanType.getLoanTypeId(), modifiedLoanType.getLoanTypeId());
        assertEquals(testLoanType.getAutomaticValidation(), modifiedLoanType.getAutomaticValidation());
    }

    @Test
    void builder_WithZeroValues_ShouldAcceptZeroValues() {
        LoanType loanType = LoanType.builder()
                .loanTypeId(0L)
                .maxAmount(BigDecimal.ZERO)
                .interestRate(BigDecimal.ZERO)
                .build();

        assertEquals(0L, loanType.getLoanTypeId());
        assertEquals(BigDecimal.ZERO, loanType.getMaxAmount());
        assertEquals(BigDecimal.ZERO, loanType.getInterestRate());
    }

    @Test
    void builder_WithNegativeValues_ShouldAcceptNegativeValues() {
        LoanType loanType = LoanType.builder()
                .maxAmount(new BigDecimal("-1000"))
                .interestRate(new BigDecimal("-5.5"))
                .build();

        assertEquals(new BigDecimal("-1000"), loanType.getMaxAmount());
        assertEquals(new BigDecimal("-5.5"), loanType.getInterestRate());
    }

    @Test
    void builder_WithEmptyName_ShouldAcceptEmptyString() {
        LoanType loanType = LoanType.builder()
                .name("")
                .build();

        assertEquals("", loanType.getName());
    }

    @Test
    void builder_WithBooleanFalse_ShouldAcceptFalseValue() {
        LoanType loanType = LoanType.builder()
                .automaticValidation(false)
                .build();

        assertFalse(loanType.getAutomaticValidation());
    }

    @Test
    void builder_WithBooleanTrue_ShouldAcceptTrueValue() {
        LoanType loanType = LoanType.builder()
                .automaticValidation(true)
                .build();

        assertTrue(loanType.getAutomaticValidation());
    }

    @Test
    void builder_WithLargeNumbers_ShouldAcceptLargeValues() {
        BigDecimal largeAmount = new BigDecimal("999999999999999999.99");
        BigDecimal largeRate = new BigDecimal("99.99");
        Long largeId = Long.MAX_VALUE;

        LoanType loanType = LoanType.builder()
                .loanTypeId(largeId)
                .maxAmount(largeAmount)
                .interestRate(largeRate)
                .build();

        assertEquals(largeId, loanType.getLoanTypeId());
        assertEquals(largeAmount, loanType.getMaxAmount());
        assertEquals(largeRate, loanType.getInterestRate());
    }

    @Test
    void builder_WithSpecialCharactersInName_ShouldAcceptSpecialCharacters() {
        String specialName = "Préstamo Especial - 100% Garantizado!";

        LoanType loanType = LoanType.builder()
                .name(specialName)
                .build();

        assertEquals(specialName, loanType.getName());
    }

    @Test
    void toBuilder_FromEmptyLoanType_ShouldCreateBuilderWithNullValues() {
        LoanType emptyLoanType = new LoanType();
        LoanType copiedLoanType = emptyLoanType.toBuilder().build();

        assertNull(copiedLoanType.getLoanTypeId());
        assertNull(copiedLoanType.getName());
        assertNull(copiedLoanType.getMaxAmount());
        assertNull(copiedLoanType.getInterestRate());
        assertNull(copiedLoanType.getAutomaticValidation());
    }

    @Test
    void builder_WithDecimalPrecision_ShouldMaintainPrecision() {
        BigDecimal preciseAmount = new BigDecimal("12345.6789");
        BigDecimal preciseRate = new BigDecimal("15.123456");

        LoanType loanType = LoanType.builder()
                .maxAmount(preciseAmount)
                .interestRate(preciseRate)
                .build();

        assertEquals(preciseAmount, loanType.getMaxAmount());
        assertEquals(preciseRate, loanType.getInterestRate());
    }
}