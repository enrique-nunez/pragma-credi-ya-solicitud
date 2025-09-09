package co.com.pragma.model.common.exceptions;

import co.com.pragma.model.common.enums.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InvalidInputException Tests")
class InvalidInputExceptionTest {

    @Test
    @DisplayName("Should create exception with error code and message")
    void shouldCreateExceptionWithErrorCodeAndMessage() {
        // Given
        ErrorCode errorCode = ErrorCode.EMAIL_REQUIRED;

        // When
        InvalidInputException exception = new InvalidInputException(errorCode);

        // Then
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals(errorCode.getMessage(), exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Should be instance of RuntimeException")
    void shouldBeInstanceOfRuntimeException() {
        // Given
        InvalidInputException exception = new InvalidInputException(ErrorCode.INVALID_EMAIL_FORMAT);

        // Then
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    @DisplayName("Should preserve error code information")
    void shouldPreserveErrorCodeInformation() {
        // Given
        ErrorCode expectedErrorCode = ErrorCode.AMOUNT_REQUIRED;

        // When
        InvalidInputException exception = new InvalidInputException(expectedErrorCode);

        // Then
        assertEquals(expectedErrorCode, exception.getErrorCode());
        assertSame(expectedErrorCode, exception.getErrorCode());
    }

    @Test
    @DisplayName("Should use error code message as exception message")
    void shouldUseErrorCodeMessageAsExceptionMessage() {
        // Given
        ErrorCode errorCode = ErrorCode.INVALID_TERM;
        String expectedMessage = errorCode.getMessage();

        // When
        InvalidInputException exception = new InvalidInputException(errorCode);

        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should be throwable and catchable")
    void shouldBeThrowableAndCatchable() {
        // Given
        ErrorCode errorCode = ErrorCode.TYPE_REQUIRED;

        // When & Then
        InvalidInputException thrownException = assertThrows(InvalidInputException.class, () -> {
            throw new InvalidInputException(errorCode);
        });

        assertEquals(errorCode, thrownException.getErrorCode());
        assertEquals(errorCode.getMessage(), thrownException.getMessage());
    }

    @Test
    @DisplayName("Should be catchable as RuntimeException")
    void shouldBeCatchableAsRuntimeException() {
        // Given
        ErrorCode errorCode = ErrorCode.INVALID_AMOUNT;

        // When & Then
        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            throw new InvalidInputException(errorCode);
        });

        assertInstanceOf(InvalidInputException.class, thrownException);
        InvalidInputException invalidInputException = (InvalidInputException) thrownException;
        assertEquals(errorCode, invalidInputException.getErrorCode());
    }

    @Test
    @DisplayName("Should maintain stack trace")
    void shouldMaintainStackTrace() {
        // Given
        InvalidInputException exception = new InvalidInputException(ErrorCode.EMAIL_REQUIRED);

        // When
        StackTraceElement[] stackTrace = exception.getStackTrace();

        // Then
        assertNotNull(stackTrace);
        assertTrue(stackTrace.length > 0);
    }

    @Test
    @DisplayName("Should work with EMAIL_REQUIRED error code")
    void shouldWorkWithEmailRequiredErrorCode() {
        // Given
        ErrorCode errorCode = ErrorCode.EMAIL_REQUIRED;

        // When
        InvalidInputException exception = new InvalidInputException(errorCode);

        // Then
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals("El correo electrónico es obligatorio", exception.getMessage());
    }

    @Test
    @DisplayName("Should work with INVALID_EMAIL_FORMAT error code")
    void shouldWorkWithInvalidEmailFormatErrorCode() {
        // Given
        ErrorCode errorCode = ErrorCode.INVALID_EMAIL_FORMAT;

        // When
        InvalidInputException exception = new InvalidInputException(errorCode);

        // Then
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals("El correo electrónico no es válido", exception.getMessage());
    }

    @Test
    @DisplayName("Should work with TERM_REQUIRED error code")
    void shouldWorkWithTermRequiredErrorCode() {
        // Given
        ErrorCode errorCode = ErrorCode.TERM_REQUIRED;

        // When
        InvalidInputException exception = new InvalidInputException(errorCode);

        // Then
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals("El plazo es obligatorio", exception.getMessage());
    }

    @Test
    @DisplayName("Should work with INVALID_TERM error code")
    void shouldWorkWithInvalidTermErrorCode() {
        // Given
        ErrorCode errorCode = ErrorCode.INVALID_TERM;

        // When
        InvalidInputException exception = new InvalidInputException(errorCode);

        // Then
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals("El plazo debe ser un número positivo", exception.getMessage());
    }

    @Test
    @DisplayName("Should work with AMOUNT_REQUIRED error code")
    void shouldWorkWithAmountRequiredErrorCode() {
        // Given
        ErrorCode errorCode = ErrorCode.AMOUNT_REQUIRED;

        // When
        InvalidInputException exception = new InvalidInputException(errorCode);

        // Then
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals("El monto es obligatorio", exception.getMessage());
    }

    @Test
    @DisplayName("Should work with INVALID_AMOUNT error code")
    void shouldWorkWithInvalidAmountErrorCode() {
        // Given
        ErrorCode errorCode = ErrorCode.INVALID_AMOUNT;

        // When
        InvalidInputException exception = new InvalidInputException(errorCode);

        // Then
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals("El monto debe ser un número positivo", exception.getMessage());
    }

    @Test
    @DisplayName("Should work with TYPE_REQUIRED error code")
    void shouldWorkWithTypeRequiredErrorCode() {
        // Given
        ErrorCode errorCode = ErrorCode.TYPE_REQUIRED;

        // When
        InvalidInputException exception = new InvalidInputException(errorCode);

        // Then
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals("El tipo de préstamo es obligatorio", exception.getMessage());
    }

    @ParameterizedTest
    @EnumSource(ErrorCode.class)
    @DisplayName("Should work with all error codes")
    void shouldWorkWithAllErrorCodes(ErrorCode errorCode) {
        // When
        InvalidInputException exception = new InvalidInputException(errorCode);

        // Then
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals(errorCode.getMessage(), exception.getMessage());
        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isEmpty());
    }

    @Test
    @DisplayName("Should support exception chaining with cause")
    void shouldSupportExceptionChainingWithCause() {
        // Given
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        RuntimeException cause = new RuntimeException("Root cause");

        // When
        InvalidInputException exception = new InvalidInputException(errorCode);
        exception.initCause(cause);

        // Then
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals(errorCode.getMessage(), exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("Should work in try-catch block")
    void shouldWorkInTryCatchBlock() {
        // Given
        ErrorCode expectedErrorCode = ErrorCode.INVALID_INPUT;

        // When & Then
        try {
            throw new InvalidInputException(expectedErrorCode);
        } catch (InvalidInputException e) {
            assertEquals(expectedErrorCode, e.getErrorCode());
            assertEquals(expectedErrorCode.getMessage(), e.getMessage());
            assertInstanceOf(RuntimeException.class, e);
        }
    }

    @Test
    @DisplayName("Should preserve error code information when caught")
    void shouldPreserveErrorCodeInformationWhenCaught() {
        // Given
        ErrorCode expectedErrorCode = ErrorCode.DATABASE_CONNECTION_ERROR;

        // When & Then
        InvalidInputException caughtException = assertThrows(InvalidInputException.class, () -> {
            throw new InvalidInputException(expectedErrorCode);
        });

        assertEquals(expectedErrorCode, caughtException.getErrorCode());
        assertEquals(expectedErrorCode.getMessage(), caughtException.getMessage());
    }

    @Test
    @DisplayName("Should create different instances with same error code")
    void shouldCreateDifferentInstancesWithSameErrorCode() {
        // Given
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;

        // When
        InvalidInputException exception1 = new InvalidInputException(errorCode);
        InvalidInputException exception2 = new InvalidInputException(errorCode);

        // Then
        assertNotSame(exception1, exception2);
        assertEquals(exception1.getErrorCode(), exception2.getErrorCode());
        assertEquals(exception1.getMessage(), exception2.getMessage());
    }

    @Test
    @DisplayName("Should work with validation error codes")
    void shouldWorkWithValidationErrorCodes() {
        ErrorCode[] validationErrorCodes = {
                ErrorCode.EMAIL_REQUIRED,
                ErrorCode.INVALID_EMAIL_FORMAT,
                ErrorCode.TERM_REQUIRED,
                ErrorCode.INVALID_TERM,
                ErrorCode.AMOUNT_REQUIRED,
                ErrorCode.INVALID_AMOUNT,
                ErrorCode.TYPE_REQUIRED,
                ErrorCode.VALIDATION_ERROR
        };

        for (ErrorCode errorCode : validationErrorCodes) {
            InvalidInputException exception = new InvalidInputException(errorCode);

            assertEquals(errorCode, exception.getErrorCode());
            assertEquals(errorCode.getMessage(), exception.getMessage());
            assertInstanceOf(RuntimeException.class, exception);
        }
    }

    @Test
    @DisplayName("Should work with business logic error codes")
    void shouldWorkWithBusinessLogicErrorCodes() {
        ErrorCode[] businessErrorCodes = {
                ErrorCode.LOAN_TYPE_NOT_EXISTS,
                ErrorCode.STATUS_LOAN_NOT_EXISTS,
                ErrorCode.USER_EMAIL_NOT_EXISTS
        };

        for (ErrorCode errorCode : businessErrorCodes) {
            InvalidInputException exception = new InvalidInputException(errorCode);

            assertEquals(errorCode, exception.getErrorCode());
            assertEquals(errorCode.getMessage(), exception.getMessage());
            assertInstanceOf(RuntimeException.class, exception);
        }
    }

    @Test
    @DisplayName("Should work with system error codes")
    void shouldWorkWithSystemErrorCodes() {
        ErrorCode[] systemErrorCodes = {
                ErrorCode.DATABASE_CONNECTION_ERROR,
                ErrorCode.DATABASE_CONSTRAINT_VIOLATION,
                ErrorCode.INTERNAL_ERROR,
                ErrorCode.ROUTE_NOT_FOUND
        };

        for (ErrorCode errorCode : systemErrorCodes) {
            InvalidInputException exception = new InvalidInputException(errorCode);

            assertEquals(errorCode, exception.getErrorCode());
            assertEquals(errorCode.getMessage(), exception.getMessage());
            assertInstanceOf(RuntimeException.class, exception);
        }
    }

    @Test
    @DisplayName("Should maintain message consistency with error code")
    void shouldMaintainMessageConsistencyWithErrorCode() {
        // Given
        ErrorCode errorCode = ErrorCode.INVALID_INPUT;

        // When
        InvalidInputException exception = new InvalidInputException(errorCode);

        // Then
        // Message should always match the error code's message
        assertEquals(errorCode.getMessage(), exception.getMessage());

        // Verify the actual message content
        assertEquals("El input es inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Should be serializable friendly")
    void shouldBeSerializableFriendly() {
        // Given
        ErrorCode errorCode = ErrorCode.EMAIL_REQUIRED;
        InvalidInputException exception = new InvalidInputException(errorCode);

        // When & Then - Basic checks for serialization compatibility
        assertNotNull(exception.getErrorCode());
        assertNotNull(exception.getMessage());
        assertDoesNotThrow(() -> exception.toString());
    }

    @Test
    @DisplayName("Should have meaningful toString representation")
    void shouldHaveMeaningfulToStringRepresentation() {
        // Given
        ErrorCode errorCode = ErrorCode.INVALID_EMAIL_FORMAT;
        InvalidInputException exception = new InvalidInputException(errorCode);

        // When
        String toStringResult = exception.toString();

        // Then
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("InvalidInputException"));
        assertTrue(toStringResult.contains(errorCode.getMessage()));
    }

    @Test
    @DisplayName("Should handle error codes with special characters in messages")
    void shouldHandleErrorCodesWithSpecialCharactersInMessages() {
        // Given - Using error codes that might have special characters
        ErrorCode[] errorCodesWithSpecialChars = {
                ErrorCode.INVALID_EMAIL_FORMAT, // Contains "no es válido"
                ErrorCode.INVALID_TERM,         // Contains "número positivo"
                ErrorCode.LOAN_TYPE_NOT_EXISTS  // Contains "préstamo"
        };

        for (ErrorCode errorCode : errorCodesWithSpecialChars) {
            // When
            InvalidInputException exception = new InvalidInputException(errorCode);

            // Then
            assertEquals(errorCode, exception.getErrorCode());
            assertEquals(errorCode.getMessage(), exception.getMessage());
            assertNotNull(exception.getMessage());
            assertFalse(exception.getMessage().isEmpty());
        }
    }
}