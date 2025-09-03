package co.com.pragma.model.common.exceptions;

public class GlobalBusinessException extends RuntimeException {
    private final Type type;

    public GlobalBusinessException(Type type, String message) {
        super(message);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        INVALID_AMOUNT("El monto es inválido"),
        INVALID_TERM("El plazo es inválido"),
        LOAN_TYPE_NOT_EXISTS("El tipo de préstamo no existe"),
        USER_NOT_EXISTS("El usuario no existe");

        private final String defaultMessage;

        Type(String defaultMessage) {
            this.defaultMessage = defaultMessage;
        }

        public GlobalBusinessException build() {
            return new GlobalBusinessException(this, defaultMessage);
        }

        public GlobalBusinessException build(String customMessage) {
            return new GlobalBusinessException(this, customMessage);
        }
    }
}
