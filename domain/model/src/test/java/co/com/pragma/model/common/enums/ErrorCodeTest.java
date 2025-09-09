package co.com.pragma.model.common.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ErrorCode Enum Tests")
class ErrorCodeTest {

    @Test
    @DisplayName("Should have correct code and message for EMAIL_REQUIRED")
    void emailRequired_ShouldHaveCorrectCodeAndMessage() {
        ErrorCode errorCode = ErrorCode.EMAIL_REQUIRED;

        assertEquals("APP_001", errorCode.getCode());
        assertEquals("El correo electrónico es obligatorio", errorCode.getMessage());
    }

    @Test
    @DisplayName("Should have correct code and message for INVALID_EMAIL_FORMAT")
    void invalidEmailFormat_ShouldHaveCorrectCodeAndMessage() {
        ErrorCode errorCode = ErrorCode.INVALID_EMAIL_FORMAT;

        assertEquals("APP_002", errorCode.getCode());
        assertEquals("El correo electrónico no es válido", errorCode.getMessage());
    }

    @Test
    @DisplayName("Should have correct code and message for TERM_REQUIRED")
    void termRequired_ShouldHaveCorrectCodeAndMessage() {
        ErrorCode errorCode = ErrorCode.TERM_REQUIRED;

        assertEquals("APP_003", errorCode.getCode());
        assertEquals("El plazo es obligatorio", errorCode.getMessage());
    }

    @Test
    @DisplayName("Should have correct code and message for INVALID_TERM")
    void invalidTerm_ShouldHaveCorrectCodeAndMessage() {
        ErrorCode errorCode = ErrorCode.INVALID_TERM;

        assertEquals("APP_004", errorCode.getCode());
        assertEquals("El plazo debe ser un número positivo", errorCode.getMessage());
    }

    @Test
    @DisplayName("Should have correct code and message for AMOUNT_REQUIRED")
    void amountRequired_ShouldHaveCorrectCodeAndMessage() {
        ErrorCode errorCode = ErrorCode.AMOUNT_REQUIRED;

        assertEquals("APP_005", errorCode.getCode());
        assertEquals("El monto es obligatorio", errorCode.getMessage());
    }

    @Test
    @DisplayName("Should have correct code and message for TYPE_REQUIRED")
    void typeRequired_ShouldHaveCorrectCodeAndMessage() {
        ErrorCode errorCode = ErrorCode.TYPE_REQUIRED;

        assertEquals("APP_006", errorCode.getCode());
        assertEquals("El tipo de préstamo es obligatorio", errorCode.getMessage());
    }

    @Test
    @DisplayName("Should have correct code and message for INVALID_AMOUNT")
    void invalidAmount_ShouldHaveCorrectCodeAndMessage() {
        ErrorCode errorCode = ErrorCode.INVALID_AMOUNT;

        assertEquals("APP_007", errorCode.getCode());
        assertEquals("El monto debe ser un número positivo", errorCode.getMessage());
    }

    @Test
    @DisplayName("Should have correct code and message for VALIDATION_ERROR")
    void validationError_ShouldHaveCorrectCodeAndMessage() {
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;

        assertEquals("APP_008", errorCode.getCode());
        assertEquals("Validation error", errorCode.getMessage());
    }

    @Test
    @DisplayName("Should have correct code and message for LOAN_TYPE_NOT_EXISTS")
    void loanTypeNotExists_ShouldHaveCorrectCodeAndMessage() {
        ErrorCode errorCode = ErrorCode.LOAN_TYPE_NOT_EXISTS;

        assertEquals("APP_009", errorCode.getCode());
        assertEquals("El tipo de préstamo no existe", errorCode.getMessage());
    }

    @Test
    @DisplayName("Should have correct code and message for STATUS_LOAN_NOT_EXISTS")
    void statusLoanNotExists_ShouldHaveCorrectCodeAndMessage() {
        ErrorCode errorCode = ErrorCode.STATUS_LOAN_NOT_EXISTS;

        assertEquals("APP_010", errorCode.getCode());
        assertEquals("El estado del préstamo no existe", errorCode.getMessage());
    }

    @Test
    @DisplayName("Should have correct code and message for USER_EMAIL_NOT_EXISTS")
    void userEmailNotExists_ShouldHaveCorrectCodeAndMessage() {
        ErrorCode errorCode = ErrorCode.USER_EMAIL_NOT_EXISTS;

        assertEquals("APP_011", errorCode.getCode());
        assertEquals("El usuario con el correo electrónico proporcionado no existe", errorCode.getMessage());
    }

    @Test
    @DisplayName("Should have correct code and message for ROUTE_NOT_FOUND")
    void routeNotFound_ShouldHaveCorrectCodeAndMessage() {
        ErrorCode errorCode = ErrorCode.ROUTE_NOT_FOUND;

        assertEquals("APP_404_ROUTE", errorCode.getCode());
        assertEquals("Route not found", errorCode.getMessage());
    }

    @Test
    @DisplayName("Should have correct code and message for DATABASE_CONNECTION_ERROR")
    void databaseConnectionError_ShouldHaveCorrectCodeAndMessage() {
        ErrorCode errorCode = ErrorCode.DATABASE_CONNECTION_ERROR;

        assertEquals("APP_500_DB", errorCode.getCode());
        assertEquals("Database connection error", errorCode.getMessage());
    }

    @Test
    @DisplayName("Should have correct code and message for DATABASE_CONSTRAINT_VIOLATION")
    void databaseConstraintViolation_ShouldHaveCorrectCodeAndMessage() {
        ErrorCode errorCode = ErrorCode.DATABASE_CONSTRAINT_VIOLATION;

        assertEquals("APP_500_CONSTRAINT", errorCode.getCode());
        assertEquals("Database constraint violation", errorCode.getMessage());
    }

    @Test
    @DisplayName("Should have correct code and message for INTERNAL_ERROR")
    void internalError_ShouldHaveCorrectCodeAndMessage() {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;

        assertEquals("APP_500", errorCode.getCode());
        assertEquals("Internal server error", errorCode.getMessage());
    }

    @Test
    @DisplayName("Should have correct code and message for INVALID_INPUT")
    void invalidInput_ShouldHaveCorrectCodeAndMessage() {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT;

        assertEquals("APP_INVALID_INPUT", errorCode.getCode());
        assertEquals("El input es inválido", errorCode.getMessage());
    }

    @ParameterizedTest
    @EnumSource(ErrorCode.class)
    @DisplayName("All error codes should have non-null and non-empty code")
    void allErrorCodes_ShouldHaveNonNullAndNonEmptyCode(ErrorCode errorCode) {
        assertNotNull(errorCode.getCode());
        assertFalse(errorCode.getCode().isEmpty());
        assertFalse(errorCode.getCode().isBlank());
    }

    @ParameterizedTest
    @EnumSource(ErrorCode.class)
    @DisplayName("All error codes should have non-null and non-empty message")
    void allErrorCodes_ShouldHaveNonNullAndNonEmptyMessage(ErrorCode errorCode) {
        assertNotNull(errorCode.getMessage());
        assertFalse(errorCode.getMessage().isEmpty());
        assertFalse(errorCode.getMessage().isBlank());
    }

    @Test
    @DisplayName("Should have exactly 16 error codes defined")
    void shouldHaveCorrectNumberOfErrorCodes() {
        ErrorCode[] errorCodes = ErrorCode.values();
        assertEquals(16, errorCodes.length);
    }

    @Test
    @DisplayName("All validation error codes should start with APP_00")
    void validationErrorCodes_ShouldStartWithCorrectPrefix() {
        ErrorCode[] validationErrors = {
                ErrorCode.EMAIL_REQUIRED,
                ErrorCode.INVALID_EMAIL_FORMAT,
                ErrorCode.TERM_REQUIRED,
                ErrorCode.INVALID_TERM,
                ErrorCode.AMOUNT_REQUIRED,
                ErrorCode.TYPE_REQUIRED,
                ErrorCode.INVALID_AMOUNT,
                ErrorCode.VALIDATION_ERROR
        };

        for (ErrorCode errorCode : validationErrors) {
            assertTrue(errorCode.getCode().startsWith("APP_00"),
                    "Validation error code should start with APP_00: " + errorCode.getCode());
        }
    }

    @Test
    @DisplayName("Database error codes should contain 500")
    void databaseErrorCodes_ShouldContain500() {
        ErrorCode[] databaseErrors = {
                ErrorCode.DATABASE_CONNECTION_ERROR,
                ErrorCode.DATABASE_CONSTRAINT_VIOLATION
        };

        for (ErrorCode errorCode : databaseErrors) {
            assertTrue(errorCode.getCode().contains("500"),
                    "Database error code should contain 500: " + errorCode.getCode());
        }
    }

    @Test
    @DisplayName("Internal error should contain 500")
    void internalError_ShouldContain500() {
        assertTrue(ErrorCode.INTERNAL_ERROR.getCode().contains("500"));
    }

    @Test
    @DisplayName("Route not found should contain 404")
    void routeNotFound_ShouldContain404() {
        assertTrue(ErrorCode.ROUTE_NOT_FOUND.getCode().contains("404"));
    }

    @Test
    @DisplayName("All error codes should have unique codes")
    void allErrorCodes_ShouldHaveUniqueCodes() {
        ErrorCode[] errorCodes = ErrorCode.values();

        for (int i = 0; i < errorCodes.length; i++) {
            for (int j = i + 1; j < errorCodes.length; j++) {
                assertNotEquals(errorCodes[i].getCode(), errorCodes[j].getCode(),
                        "Error codes should be unique: " + errorCodes[i].name() +
                                " and " + errorCodes[j].name() + " both have code: " + errorCodes[i].getCode());
            }
        }
    }

    @Test
    @DisplayName("Error codes should be accessible by name")
    void errorCodes_ShouldBeAccessibleByName() {
        assertEquals(ErrorCode.EMAIL_REQUIRED, ErrorCode.valueOf("EMAIL_REQUIRED"));
        assertEquals(ErrorCode.INVALID_EMAIL_FORMAT, ErrorCode.valueOf("INVALID_EMAIL_FORMAT"));
        assertEquals(ErrorCode.INTERNAL_ERROR, ErrorCode.valueOf("INTERNAL_ERROR"));
        assertEquals(ErrorCode.DATABASE_CONNECTION_ERROR, ErrorCode.valueOf("DATABASE_CONNECTION_ERROR"));
    }

    @Test
    @DisplayName("Should throw exception for invalid enum name")
    void valueOf_WithInvalidName_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            ErrorCode.valueOf("INVALID_ERROR_CODE");
        });
    }

    @Test
    @DisplayName("toString should return enum name")
    void toString_ShouldReturnEnumName() {
        assertEquals("EMAIL_REQUIRED", ErrorCode.EMAIL_REQUIRED.toString());
        assertEquals("INTERNAL_ERROR", ErrorCode.INTERNAL_ERROR.toString());
        assertEquals("DATABASE_CONNECTION_ERROR", ErrorCode.DATABASE_CONNECTION_ERROR.toString());
    }

    @Test
    @DisplayName("Spanish error messages should be properly formatted")
    void spanishErrorMessages_ShouldBeProperlyFormatted() {
        ErrorCode[] spanishErrors = {
                ErrorCode.EMAIL_REQUIRED,
                ErrorCode.INVALID_EMAIL_FORMAT,
                ErrorCode.TERM_REQUIRED,
                ErrorCode.INVALID_TERM,
                ErrorCode.AMOUNT_REQUIRED,
                ErrorCode.TYPE_REQUIRED,
                ErrorCode.INVALID_AMOUNT,
                ErrorCode.LOAN_TYPE_NOT_EXISTS,
                ErrorCode.STATUS_LOAN_NOT_EXISTS,
                ErrorCode.USER_EMAIL_NOT_EXISTS,
                ErrorCode.INVALID_INPUT
        };

        for (ErrorCode errorCode : spanishErrors) {
            String message = errorCode.getMessage();
            // Verify Spanish messages start with capital letter and don't end with period
            assertTrue(Character.isUpperCase(message.charAt(0)) || message.charAt(0) == 'E',
                    "Spanish message should start with capital letter: " + message);
            assertFalse(message.endsWith("."),
                    "Spanish message should not end with period: " + message);
        }
    }

    @Test
    @DisplayName("English error messages should be properly formatted")
    void englishErrorMessages_ShouldBeProperlyFormatted() {
        ErrorCode[] englishErrors = {
                ErrorCode.VALIDATION_ERROR,
                ErrorCode.ROUTE_NOT_FOUND,
                ErrorCode.DATABASE_CONNECTION_ERROR,
                ErrorCode.DATABASE_CONSTRAINT_VIOLATION,
                ErrorCode.INTERNAL_ERROR
        };

        for (ErrorCode errorCode : englishErrors) {
            String message = errorCode.getMessage();
            // Verify English messages start with capital letter and don't end with period
            assertTrue(Character.isUpperCase(message.charAt(0)),
                    "English message should start with capital letter: " + message);
            assertFalse(message.endsWith("."),
                    "English message should not end with period: " + message);
        }
    }

    @Test
    @DisplayName("Error codes should follow naming convention")
    void errorCodes_ShouldFollowNamingConvention() {
        ErrorCode[] errorCodes = ErrorCode.values();

        for (ErrorCode errorCode : errorCodes) {
            String name = errorCode.name();
            // Should be all uppercase
            assertEquals(name.toUpperCase(), name,
                    "Error code name should be uppercase: " + name);
            // Should use underscores for word separation
            assertFalse(name.contains("-"),
                    "Error code name should use underscores, not hyphens: " + name);
            assertFalse(name.contains(" "),
                    "Error code name should not contain spaces: " + name);
        }
    }

    @Test
    @DisplayName("Code prefixes should be consistent")
    void codePrefixes_ShouldBeConsistent() {
        ErrorCode[] errorCodes = ErrorCode.values();

        for (ErrorCode errorCode : errorCodes) {
            String code = errorCode.getCode();
            assertTrue(code.startsWith("APP_"),
                    "All error codes should start with APP_: " + code);
        }
    }

    @Test
    @DisplayName("Should be able to iterate through all error codes")
    void shouldBeAbleToIterateThroughAllErrorCodes() {
        int count = 0;
        for (ErrorCode errorCode : ErrorCode.values()) {
            assertNotNull(errorCode);
            assertNotNull(errorCode.getCode());
            assertNotNull(errorCode.getMessage());
            count++;
        }
        assertEquals(16, count);
    }

    @Test
    @DisplayName("Enum should be comparable")
    void enum_ShouldBeComparable() {
        // Test ordinal comparison
        assertTrue(ErrorCode.EMAIL_REQUIRED.ordinal() < ErrorCode.INVALID_EMAIL_FORMAT.ordinal());
        assertTrue(ErrorCode.INTERNAL_ERROR.ordinal() > ErrorCode.EMAIL_REQUIRED.ordinal());

        // Test compareTo
        assertTrue(ErrorCode.EMAIL_REQUIRED.compareTo(ErrorCode.INVALID_EMAIL_FORMAT) < 0);
        assertTrue(ErrorCode.INVALID_EMAIL_FORMAT.compareTo(ErrorCode.EMAIL_REQUIRED) > 0);
        assertEquals(0, ErrorCode.EMAIL_REQUIRED.compareTo(ErrorCode.EMAIL_REQUIRED));
    }
}