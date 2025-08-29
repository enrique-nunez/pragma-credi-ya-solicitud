package co.com.pragma.model.common.exceptions;

import co.com.pragma.model.common.enums.ErrorCode;

public class InvalidInputException extends RuntimeException {

    private final ErrorCode errorCode;

    public InvalidInputException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
