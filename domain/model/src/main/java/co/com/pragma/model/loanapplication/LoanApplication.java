package co.com.pragma.model.loanapplication;

import co.com.pragma.model.common.enums.TypeStatusLoan;
import java.math.BigDecimal;

public class LoanApplication {
    private Long idLoan;
    private BigDecimal amount;
    private Integer term;
    private String email;
    private Long idStatus;
    private Long loanTypeId;

    public LoanApplication() {
    }

    public LoanApplication(Long idLoan, BigDecimal amount, Integer term, String email, Long idStatus, Long loanTypeId) {
        this.idLoan = idLoan;
        this.amount = amount;
        this.term = term;
        this.email = email;
        this.idStatus = idStatus;
        this.loanTypeId = loanTypeId;
    }

    // Getters
    public Long getIdLoan() { return idLoan; }
    public BigDecimal getAmount() { return amount; }
    public Integer getTerm() { return term; }
    public String getEmail() { return email; }
    public Long getIdStatus() { return idStatus; }
    public Long getLoanTypeId() { return loanTypeId; }

    // Setters
    public void setIdLoan(Long idLoan) { this.idLoan = idLoan; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setTerm(Integer term) { this.term = term; }
    public void setEmail(String email) { this.email = email; }
    public void setIdStatus(Long idStatus) { this.idStatus = idStatus; }
    public void setLoanTypeId(Long loanTypeId) { this.loanTypeId = loanTypeId; }

    // Builder pattern
    public static LoanBuilder builder() {
        return new LoanBuilder();
    }

    public LoanBuilder toBuilder() {
        return new LoanBuilder()
                .idLoan(this.idLoan)
                .amount(this.amount)
                .term(this.term)
                .email(this.email)
                .idStatus(this.idStatus)
                .loanTypeId(this.loanTypeId);
    }

    public static class LoanBuilder {
        private Long idLoan;
        private BigDecimal amount;
        private Integer term;
        private String email;
        private Long idStatus;
        private Long loanTypeId;

        public LoanBuilder idLoan(Long idLoan) { this.idLoan = idLoan; return this; }
        public LoanBuilder amount(BigDecimal amount) { this.amount = amount; return this; }
        public LoanBuilder term(Integer term) { this.term = term; return this; }
        public LoanBuilder email(String email) { this.email = email; return this; }
        public LoanBuilder idStatus(Long idStatus) { this.idStatus = idStatus; return this; }
        public LoanBuilder loanTypeId(Long loanTypeId) { this.loanTypeId = loanTypeId; return this; }

        public LoanApplication build() {
            return new LoanApplication(idLoan, amount, term, email, idStatus, loanTypeId);
        }
    }

    public LoanApplication defaultStatusLoan() {
        this.idStatus = TypeStatusLoan.PENDING.getValue();
        return this;
    }
}