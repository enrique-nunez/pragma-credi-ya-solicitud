package co.com.pragma.model.common.enums;

public enum ErrorCode {
    // Validation errors (400)
    EMAIL_REQUIRED("APP_001", "El correo electrónico es obligatorio"),
    INVALID_EMAIL_FORMAT("APP_002", "El correo electrónico no es válido"),
    TERM_REQUIRED("APP_003", "El plazo es obligatorio"),
    INVALID_TERM("APP_004", "El plazo debe ser un número positivo"),
    AMOUNT_REQUIRED("APP_005", "El monto es obligatorio"),
    TYPE_REQUIRED("APP_006", "El tipo de préstamo es obligatorio"),
    INVALID_AMOUNT("APP_007", "El monto debe ser un número positivo"),

    VALIDATION_ERROR("APP_008", "Validation error"),
    LOAN_TYPE_NOT_EXISTS("APP_009", "El tipo de préstamo no existe"),
    STATUS_LOAN_NOT_EXISTS("APP_010", "El estado del préstamo no existe"),
    USER_EMAIL_NOT_EXISTS("APP_011", "El usuario con el correo electrónico proporcionado no existe"),
    ROUTE_NOT_FOUND("APP_404_ROUTE", "Route not found"),

    // Database errors (500)
    DATABASE_CONNECTION_ERROR("APP_500_DB", "Database connection error"),
    DATABASE_CONSTRAINT_VIOLATION("APP_500_CONSTRAINT", "Database constraint violation"),

    // Internal errors (500)
    INTERNAL_ERROR("APP_500", "Internal server error"),
    INVALID_INPUT("APP_INVALID_INPUT", "El input es inválido");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
