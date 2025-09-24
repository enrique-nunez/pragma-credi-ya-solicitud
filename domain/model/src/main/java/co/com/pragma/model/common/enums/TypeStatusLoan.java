package co.com.pragma.model.common.enums;

public enum TypeStatusLoan {
    PENDING(1L, "PENDING"),
    APPROVED(2L, "APPROVED"),
    REJECTED(3L, "REJECTED");

    private final Long value;
    private final String description;

    TypeStatusLoan(Long value, String description) {
        this.value = value;
        this.description = description;
    }

    public Long getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
