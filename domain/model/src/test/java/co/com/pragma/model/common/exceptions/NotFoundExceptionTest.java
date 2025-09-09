package co.com.pragma.model.common.exceptions;

import co.com.pragma.model.common.enums.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NotFoundException Tests")
class NotFoundExceptionTest {

    @Test
    @DisplayName("Should create exception with error code and message")
    void shouldCreateExceptionWithErrorCodeAndMessage() {
        // Given
        ErrorCode errorCode = ErrorCode.LOAN_TYPE_NOT_EXISTS;

        // When
        NotFoundException exception = new NotFoundException(errorCode);

        // Then
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals(errorCode.getMessage(), exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Should be instance of RuntimeException")
    void shouldBeInstanceOfRuntimeException() {
        // Given
        NotFoundException exception = new NotFoundException(ErrorCode.USER_EMAIL_NOT_EXISTS);

        // Then
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    @DisplayName("Should preserve error code information")
    void shouldPreserveErrorCodeInformation() {
        // Given
        ErrorCode expectedErrorCode = ErrorCode.STATUS_LOAN_NOT_EXISTS;

        // When
        NotFoundException exception = new NotFoundException(expectedErrorCode);

        // Then
        assertEquals(expectedErrorCode, exception.getErrorCode());
        assertSame(expectedErrorCode, exception.getErrorCode());
    }

    @Test
    @DisplayName("Should use error code message as exception message")
    void shouldUseErrorCodeMessageAsExceptionMessage() {
        // Given
        ErrorCode errorCode = ErrorCode.LOAN_TYPE_NOT_EXISTS;
        String expectedMessage = errorCode.getMessage();

        // When
        NotFoundException exception = new NotFoundException(errorCode);

        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should be throwable and catchable")
    void shouldBeThrowableAndCatchable() {
        // Given
        ErrorCode errorCode = ErrorCode.USER_EMAIL_NOT_EXISTS;

        // When & Then
        NotFoundException thrownException = assertThrows(NotFoundException.class, () -> {
            throw new NotFoundException(errorCode);
        });

        assertEquals(errorCode, thrownException.getErrorCode());
        assertEquals(errorCode.getMessage(), thrownException.getMessage());
    }

    @Test
    @DisplayName("Should be catchable as RuntimeException")
    void shouldBeCatchableAsRuntimeException() {
        // Given
        ErrorCode errorCode = ErrorCode.LOAN_TYPE_NOT_EXISTS;

        // When & Then
        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            throw new NotFoundException(errorCode);
        });

        assertInstanceOf(NotFoundException.class, thrownException);
        NotFoundException notFoundException = (NotFoundException) thrownException;
        assertEquals(errorCode, notFoundException.getErrorCode());
    }

    @Test
    @DisplayName("Should maintain stack trace")
    void shouldMaintainStackTrace() {
        // Given
        NotFoundException exception = new NotFoundException(ErrorCode.LOAN_TYPE_NOT_EXISTS);

        // When
        StackTraceElement[] stackTrace = exception.getStackTrace();

        // Then
        assertNotNull(stackTrace);
        assertTrue(stackTrace.length > 0);
    }

    @Test
    @DisplayName("Should work with LOAN_TYPE_NOT_EXISTS error code")
    void shouldWorkWithLoanTypeNotExistsErrorCode() {
        // Given
        ErrorCode errorCode = ErrorCode.LOAN_TYPE_NOT_EXISTS;

        // When
        NotFoundException exception = new NotFoundException(errorCode);

        // Then
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals("El tipo de préstamo no existe", exception.getMessage());
    }

    @Test
    @DisplayName("Should work with STATUS_LOAN_NOT_EXISTS error code")
    void shouldWorkWithStatusLoanNotExistsErrorCode() {
        // Given
        ErrorCode errorCode = ErrorCode.STATUS_LOAN_NOT_EXISTS;

        // When
        NotFoundException exception = new NotFoundException(errorCode);

        // Then
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals("El estado del préstamo no existe", exception.getMessage());
    }

    @Test
    @DisplayName("Should work with USER_EMAIL_NOT_EXISTS error code")
    void shouldWorkWithUserEmailNotExistsErrorCode() {
        // Given
        ErrorCode errorCode = ErrorCode.USER_EMAIL_NOT_EXISTS;

        // When
        NotFoundException exception = new NotFoundException(errorCode);

        // Then
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals("El usuario con el correo electrónico proporcionado no existe", exception.getMessage());
    }

    @Test
    @DisplayName("Should work with ROUTE_NOT_FOUND error code")
    void shouldWorkWithRouteNotFoundErrorCode() {
        // Given
        ErrorCode errorCode = ErrorCode.ROUTE_NOT_FOUND;

        // When
        NotFoundException exception = new NotFoundException(errorCode);

        // Then
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals("Route not found", exception.getMessage());
    }

    @ParameterizedTest
    @EnumSource(ErrorCode.class)
    @DisplayName("Should work with all error codes")
    void shouldWorkWithAllErrorCodes(ErrorCode errorCode) {
        // When
        NotFoundException exception = new NotFoundException(errorCode);

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
        ErrorCode errorCode = ErrorCode.LOAN_TYPE_NOT_EXISTS;
        RuntimeException cause = new RuntimeException("Database query failed");

        // When
        NotFoundException exception = new NotFoundException(errorCode);
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
        ErrorCode expectedErrorCode = ErrorCode.USER_EMAIL_NOT_EXISTS;

        // When & Then
        try {
            throw new NotFoundException(expectedErrorCode);
        } catch (NotFoundException e) {
            assertEquals(expectedErrorCode, e.getErrorCode());
            assertEquals(expectedErrorCode.getMessage(), e.getMessage());
            assertInstanceOf(RuntimeException.class, e);
        }
    }

    @Test
    @DisplayName("Should preserve error code information when caught")
    void shouldPreserveErrorCodeInformationWhenCaught() {
        // Given
        ErrorCode expectedErrorCode = ErrorCode.STATUS_LOAN_NOT_EXISTS;

        // When & Then
        NotFoundException caughtException = assertThrows(NotFoundException.class, () -> {
            throw new NotFoundException(expectedErrorCode);
        });

        assertEquals(expectedErrorCode, caughtException.getErrorCode());
        assertEquals(expectedErrorCode.getMessage(), caughtException.getMessage());
    }

    @Test
    @DisplayName("Should create different instances with same error code")
    void shouldCreateDifferentInstancesWithSameErrorCode() {
        // Given
        ErrorCode errorCode = ErrorCode.LOAN_TYPE_NOT_EXISTS;

        // When
        NotFoundException exception1 = new NotFoundException(errorCode);
        NotFoundException exception2 = new NotFoundException(errorCode);

        // Then
        assertNotSame(exception1, exception2);
        assertEquals(exception1.getErrorCode(), exception2.getErrorCode());
        assertEquals(exception1.getMessage(), exception2.getMessage());
    }

    @Test
    @DisplayName("Should work with not-found related error codes")
    void shouldWorkWithNotFoundRelatedErrorCodes() {
        ErrorCode[] notFoundErrorCodes = {
                ErrorCode.LOAN_TYPE_NOT_EXISTS,
                ErrorCode.STATUS_LOAN_NOT_EXISTS,
                ErrorCode.USER_EMAIL_NOT_EXISTS,
                ErrorCode.ROUTE_NOT_FOUND
        };

        for (ErrorCode errorCode : notFoundErrorCodes) {
            NotFoundException exception = new NotFoundException(errorCode);

            assertEquals(errorCode, exception.getErrorCode());
            assertEquals(errorCode.getMessage(), exception.getMessage());
            assertInstanceOf(RuntimeException.class, exception);
        }
    }

    @Test
    @DisplayName("Should work with validation error codes even though semantically different")
    void shouldWorkWithValidationErrorCodesEvenThoughSemanticallyDifferent() {
        // Note: While semantically NotFoundException should be used for "not found" scenarios,
        // the class technically accepts any ErrorCode
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
            NotFoundException exception = new NotFoundException(errorCode);

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
                ErrorCode.INVALID_INPUT
        };

        for (ErrorCode errorCode : systemErrorCodes) {
            NotFoundException exception = new NotFoundException(errorCode);

            assertEquals(errorCode, exception.getErrorCode());
            assertEquals(errorCode.getMessage(), exception.getMessage());
            assertInstanceOf(RuntimeException.class, exception);
        }
    }

    @Test
    @DisplayName("Should maintain message consistency with error code")
    void shouldMaintainMessageConsistencyWithErrorCode() {
        // Given
        ErrorCode errorCode = ErrorCode.LOAN_TYPE_NOT_EXISTS;

        // When
        NotFoundException exception = new NotFoundException(errorCode);

        // Then
        // Message should always match the error code's message
        assertEquals(errorCode.getMessage(), exception.getMessage());

        // Verify the actual message content
        assertEquals("El tipo de préstamo no existe", exception.getMessage());
    }

    @Test
    @DisplayName("Should be serializable friendly")
    void shouldBeSerializableFriendly() {
        // Given
        ErrorCode errorCode = ErrorCode.USER_EMAIL_NOT_EXISTS;
        NotFoundException exception = new NotFoundException(errorCode);

        // When & Then - Basic checks for serialization compatibility
        assertNotNull(exception.getErrorCode());
        assertNotNull(exception.getMessage());
        assertDoesNotThrow(() -> exception.toString());
    }

    @Test
    @DisplayName("Should have meaningful toString representation")
    void shouldHaveMeaningfulToStringRepresentation() {
        // Given
        ErrorCode errorCode = ErrorCode.LOAN_TYPE_NOT_EXISTS;
        NotFoundException exception = new NotFoundException(errorCode);

        // When
        String toStringResult = exception.toString();

        // Then
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("NotFoundException"));
        assertTrue(toStringResult.contains(errorCode.getMessage()));
    }

    @Test
    @DisplayName("Should handle error codes with special characters in messages")
    void shouldHandleErrorCodesWithSpecialCharactersInMessages() {
        // Given - Using error codes that might have special characters
        ErrorCode[] errorCodesWithSpecialChars = {
                ErrorCode.LOAN_TYPE_NOT_EXISTS,  // Contains "préstamo"
                ErrorCode.USER_EMAIL_NOT_EXISTS  // Contains "electrónico"
        };

        for (ErrorCode errorCode : errorCodesWithSpecialChars) {
            // When
            NotFoundException exception = new NotFoundException(errorCode);

            // Then
            assertEquals(errorCode, exception.getErrorCode());
            assertEquals(errorCode.getMessage(), exception.getMessage());
            assertNotNull(exception.getMessage());
            assertFalse(exception.getMessage().isEmpty());
        }
    }

    @Test
    @DisplayName("Should be appropriate for resource not found scenarios")
    void shouldBeAppropriateForResourceNotFoundScenarios() {
        // Given - Error codes that semantically represent "not found" scenarios
        ErrorCode[] resourceNotFoundCodes = {
                ErrorCode.LOAN_TYPE_NOT_EXISTS,
                ErrorCode.STATUS_LOAN_NOT_EXISTS,
                ErrorCode.USER_EMAIL_NOT_EXISTS,
                ErrorCode.ROUTE_NOT_FOUND
        };

        for (ErrorCode errorCode : resourceNotFoundCodes) {
            // When
            NotFoundException exception = new NotFoundException(errorCode);

            // Then - Verify it's suitable for "not found" scenarios
            assertEquals(errorCode, exception.getErrorCode());
            assertTrue(errorCode.getMessage().contains("no existe") ||
                            errorCode.getMessage().contains("not found"),
                    "Error code should represent a 'not found' scenario: " + errorCode.getMessage());
        }
    }

    @Test
    @DisplayName("Should work in nested exception scenarios")
    void shouldWorkInNestedExceptionScenarios() {
        // Given
        ErrorCode errorCode = ErrorCode.LOAN_TYPE_NOT_EXISTS;

        // When & Then - Test nested exception handling
        RuntimeException outerException = assertThrows(RuntimeException.class, () -> {
            try {
                throw new NotFoundException(errorCode);
            } catch (NotFoundException e) {
                throw new RuntimeException("Outer exception", e);
            }
        });

        assertInstanceOf(NotFoundException.class, outerException.getCause());
        NotFoundException innerException = (NotFoundException) outerException.getCause();
        assertEquals(errorCode, innerException.getErrorCode());
    }

    @Test
    @DisplayName("Should maintain error code reference integrity")
    void shouldMaintainErrorCodeReferenceIntegrity() {
        // Given
        ErrorCode originalErrorCode = ErrorCode.USER_EMAIL_NOT_EXISTS;

        // When
        NotFoundException exception = new NotFoundException(originalErrorCode);

        // Then
        assertSame(originalErrorCode, exception.getErrorCode());
        assertEquals(originalErrorCode.getCode(), exception.getErrorCode().getCode());
        assertEquals(originalErrorCode.getMessage(), exception.getErrorCode().getMessage());
    }

    @Test
    @DisplayName("Should support multiple catch blocks")
    void shouldSupportMultipleCatchBlocks() {
        // Given
        ErrorCode errorCode = ErrorCode.ROUTE_NOT_FOUND;
        boolean notFoundCaught = false;
        boolean runtimeCaught = false;

        // When & Then
        try {
            throw new NotFoundException(errorCode);
        } catch (NotFoundException e) {
            notFoundCaught = true;
            assertEquals(errorCode, e.getErrorCode());
        } catch (RuntimeException e) {
            runtimeCaught = true;
        }

        assertTrue(notFoundCaught);
        assertFalse(runtimeCaught); // Should be caught by the more specific catch block
    }
}