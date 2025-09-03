package co.com.pragma.model.common.enums;

public enum TypeStatusLoan {
    PENDING(1L, "Pendiente de revisión"),
    APPROVED(2L, "El préstamo ha sido aprobado"),
    REJECTED(3L, "El préstamo ha sido rechazado"),;

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
