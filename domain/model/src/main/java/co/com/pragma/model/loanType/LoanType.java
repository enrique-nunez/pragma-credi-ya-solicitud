package co.com.pragma.model.loanType;

import java.math.BigDecimal;

public class LoanType {
    private Long loanTypeId;
    private String name;
    private BigDecimal maxAmount;
    private BigDecimal interestRate;
    private Boolean automaticValidation;

    public LoanType() {
    }

    public LoanType(Long loanTypeId, String name, BigDecimal maxAmount, BigDecimal interestRate, Boolean automaticValidation) {
        this.loanTypeId = loanTypeId;
        this.name = name;
        this.maxAmount = maxAmount;
        this.interestRate = interestRate;
        this.automaticValidation = automaticValidation;
    }

    // Getters
    public Long getLoanTypeId() { return loanTypeId; }
    public String getName() { return name; }
    public BigDecimal getMaxAmount() { return maxAmount; }
    public BigDecimal getInterestRate() { return interestRate; }
    public Boolean getAutomaticValidation() { return automaticValidation; }

    // Setters
    public void setLoanTypeId(Long loanTypeId) { this.loanTypeId = loanTypeId; }
    public void setName(String name) { this.name = name; }
    public void setMaxAmount(BigDecimal maxAmount) { this.maxAmount = maxAmount; }
    public void setInterestRate(BigDecimal interestRate) { this.interestRate = interestRate; }
    public void setAutomaticValidation(Boolean automaticValidation) { this.automaticValidation = automaticValidation; }

    // Builder pattern
    public static LoanTypeBuilder builder() {
        return new LoanTypeBuilder();
    }

    public LoanTypeBuilder toBuilder() {
        return new LoanTypeBuilder()
                .loanTypeId(this.loanTypeId)
                .name(this.name)
                .maxAmount(this.maxAmount)
                .interestRate(this.interestRate)
                .automaticValidation(this.automaticValidation);
    }

    public static class LoanTypeBuilder {
        private Long loanTypeId;
        private String name;
        private BigDecimal maxAmount;
        private BigDecimal interestRate;
        private Boolean automaticValidation;

        public LoanTypeBuilder loanTypeId(Long loanTypeId) { this.loanTypeId = loanTypeId; return this; }
        public LoanTypeBuilder name(String name) { this.name = name; return this; }
        public LoanTypeBuilder maxAmount(BigDecimal maxAmount) { this.maxAmount = maxAmount; return this; }
        public LoanTypeBuilder interestRate(BigDecimal interestRate) { this.interestRate = interestRate; return this; }
        public LoanTypeBuilder automaticValidation(Boolean automaticValidation) { this.automaticValidation = automaticValidation; return this; }

        public LoanType build() {
            return new LoanType(loanTypeId, name, maxAmount, interestRate, automaticValidation);
        }
    }
}
