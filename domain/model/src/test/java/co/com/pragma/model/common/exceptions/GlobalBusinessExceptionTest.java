package co.com.pragma.model.common.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GlobalBusinessException Tests")
class GlobalBusinessExceptionTest {

    @Test
    @DisplayName("Should create exception with type and message")
    void shouldCreateExceptionWithTypeAndMessage() {
        // Given
        GlobalBusinessException.Type type = GlobalBusinessException.Type.INVALID_AMOUNT;
        String message = "Custom error message";

        // When
        GlobalBusinessException exception = new GlobalBusinessException(type, message);

        // Then
        assertEquals(type, exception.getType());
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Should be instance of RuntimeException")
    void shouldBeInstanceOfRuntimeException() {
        // Given
        GlobalBusinessException exception = new GlobalBusinessException(
                GlobalBusinessException.Type.INVALID_AMOUNT,
                "Test message"
        );

        // Then
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    @DisplayName("Should preserve type information")
    void shouldPreserveTypeInformation() {
        // Given
        GlobalBusinessException.Type expectedType = GlobalBusinessException.Type.LOAN_TYPE_NOT_EXISTS;

        // When
        GlobalBusinessException exception = new GlobalBusinessException(expectedType, "Test");

        // Then
        assertEquals(expectedType, exception.getType());
        assertSame(expectedType, exception.getType());
    }

    @Test
    @DisplayName("Should handle null message")
    void shouldHandleNullMessage() {
        // Given
        GlobalBusinessException.Type type = GlobalBusinessException.Type.USER_NOT_EXISTS;

        // When
        GlobalBusinessException exception = new GlobalBusinessException(type, null);

        // Then
        assertEquals(type, exception.getType());
        assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Should handle empty message")
    void shouldHandleEmptyMessage() {
        // Given
        GlobalBusinessException.Type type = GlobalBusinessException.Type.INVALID_TERM;
        String emptyMessage = "";

        // When
        GlobalBusinessException exception = new GlobalBusinessException(type, emptyMessage);

        // Then
        assertEquals(type, exception.getType());
        assertEquals(emptyMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should be throwable")
    void shouldBeThrowable() {
        // Given
        GlobalBusinessException exception = new GlobalBusinessException(
                GlobalBusinessException.Type.INVALID_AMOUNT,
                "Test exception"
        );

        // When & Then
        assertThrows(GlobalBusinessException.class, () -> {
            throw exception;
        });
    }

    // Tests for Type enum
    @Test
    @DisplayName("Type INVALID_AMOUNT should have correct default message")
    void typeInvalidAmount_ShouldHaveCorrectDefaultMessage() {
        GlobalBusinessException.Type type = GlobalBusinessException.Type.INVALID_AMOUNT;
        assertEquals("El monto es inválido", type.defaultMessage);
    }

    @Test
    @DisplayName("Type INVALID_TERM should have correct default message")
    void typeInvalidTerm_ShouldHaveCorrectDefaultMessage() {
        GlobalBusinessException.Type type = GlobalBusinessException.Type.INVALID_TERM;
        assertEquals("El plazo es inválido", type.defaultMessage);
    }

    @Test
    @DisplayName("Type LOAN_TYPE_NOT_EXISTS should have correct default message")
    void typeLoanTypeNotExists_ShouldHaveCorrectDefaultMessage() {
        GlobalBusinessException.Type type = GlobalBusinessException.Type.LOAN_TYPE_NOT_EXISTS;
        assertEquals("El tipo de préstamo no existe", type.defaultMessage);
    }

    @Test
    @DisplayName("Type USER_NOT_EXISTS should have correct default message")
    void typeUserNotExists_ShouldHaveCorrectDefaultMessage() {
        GlobalBusinessException.Type type = GlobalBusinessException.Type.USER_NOT_EXISTS;
        assertEquals("El usuario no existe", type.defaultMessage);
    }

    @Test
    @DisplayName("Should have exactly 4 exception types defined")
    void shouldHaveCorrectNumberOfExceptionTypes() {
        GlobalBusinessException.Type[] types = GlobalBusinessException.Type.values();
        assertEquals(4, types.length);
    }

    @ParameterizedTest
    @EnumSource(GlobalBusinessException.Type.class)
    @DisplayName("All types should have non-null and non-empty default messages")
    void allTypes_ShouldHaveNonNullAndNonEmptyDefaultMessages(GlobalBusinessException.Type type) {
        assertNotNull(type.defaultMessage);
        assertFalse(type.defaultMessage.isEmpty());
        assertFalse(type.defaultMessage.isBlank());
    }

    @Test
    @DisplayName("Type build() should create exception with default message")
    void typeBuild_ShouldCreateExceptionWithDefaultMessage() {
        // Given
        GlobalBusinessException.Type type = GlobalBusinessException.Type.INVALID_AMOUNT;

        // When
        GlobalBusinessException exception = type.build();

        // Then
        assertEquals(type, exception.getType());
        assertEquals("El monto es inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Type build(customMessage) should create exception with custom message")
    void typeBuildWithCustomMessage_ShouldCreateExceptionWithCustomMessage() {
        // Given
        GlobalBusinessException.Type type = GlobalBusinessException.Type.LOAN_TYPE_NOT_EXISTS;
        String customMessage = "Custom loan type error message";

        // When
        GlobalBusinessException exception = type.build(customMessage);

        // Then
        assertEquals(type, exception.getType());
        assertEquals(customMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Type build() should create different instances")
    void typeBuild_ShouldCreateDifferentInstances() {
        // Given
        GlobalBusinessException.Type type = GlobalBusinessException.Type.USER_NOT_EXISTS;

        // When
        GlobalBusinessException exception1 = type.build();
        GlobalBusinessException exception2 = type.build();

        // Then
        assertNotSame(exception1, exception2);
        assertEquals(exception1.getType(), exception2.getType());
        assertEquals(exception1.getMessage(), exception2.getMessage());
    }

    @Test
    @DisplayName("Type build(null) should create exception with null message")
    void typeBuildWithNull_ShouldCreateExceptionWithNullMessage() {
        // Given
        GlobalBusinessException.Type type = GlobalBusinessException.Type.INVALID_TERM;

        // When
        GlobalBusinessException exception = type.build(null);

        // Then
        assertEquals(type, exception.getType());
        assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Type build(\"\") should create exception with empty message")
    void typeBuildWithEmptyString_ShouldCreateExceptionWithEmptyMessage() {
        // Given
        GlobalBusinessException.Type type = GlobalBusinessException.Type.INVALID_AMOUNT;

        // When
        GlobalBusinessException exception = type.build("");

        // Then
        assertEquals(type, exception.getType());
        assertEquals("", exception.getMessage());
    }

    @Test
    @DisplayName("All type build() methods should be throwable")
    void allTypeBuildMethods_ShouldBeThrowable() {
        GlobalBusinessException.Type[] types = GlobalBusinessException.Type.values();

        for (GlobalBusinessException.Type type : types) {
            GlobalBusinessException exception = type.build();

            assertThrows(GlobalBusinessException.class, () -> {
                throw exception;
            });
        }
    }

    @Test
    @DisplayName("Exception should maintain stack trace")
    void exception_ShouldMaintainStackTrace() {
        // Given
        GlobalBusinessException exception = GlobalBusinessException.Type.INVALID_AMOUNT.build();

        // When
        StackTraceElement[] stackTrace = exception.getStackTrace();

        // Then
        assertNotNull(stackTrace);
        assertTrue(stackTrace.length > 0);
    }

    @Test
    @DisplayName("Exception types should be accessible by name")
    void exceptionTypes_ShouldBeAccessibleByName() {
        assertEquals(GlobalBusinessException.Type.INVALID_AMOUNT,
                GlobalBusinessException.Type.valueOf("INVALID_AMOUNT"));
        assertEquals(GlobalBusinessException.Type.INVALID_TERM,
                GlobalBusinessException.Type.valueOf("INVALID_TERM"));
        assertEquals(GlobalBusinessException.Type.LOAN_TYPE_NOT_EXISTS,
                GlobalBusinessException.Type.valueOf("LOAN_TYPE_NOT_EXISTS"));
        assertEquals(GlobalBusinessException.Type.USER_NOT_EXISTS,
                GlobalBusinessException.Type.valueOf("USER_NOT_EXISTS"));
    }

    @Test
    @DisplayName("Should throw exception for invalid type name")
    void valueOf_WithInvalidTypeName_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            GlobalBusinessException.Type.valueOf("INVALID_TYPE");
        });
    }

    @Test
    @DisplayName("Type toString should return enum name")
    void typeToString_ShouldReturnEnumName() {
        assertEquals("INVALID_AMOUNT", GlobalBusinessException.Type.INVALID_AMOUNT.toString());
        assertEquals("INVALID_TERM", GlobalBusinessException.Type.INVALID_TERM.toString());
        assertEquals("LOAN_TYPE_NOT_EXISTS", GlobalBusinessException.Type.LOAN_TYPE_NOT_EXISTS.toString());
        assertEquals("USER_NOT_EXISTS", GlobalBusinessException.Type.USER_NOT_EXISTS.toString());
    }

    @Test
    @DisplayName("Exception should support chaining")
    void exception_ShouldSupportChaining() {
        // Given
        GlobalBusinessException.Type type = GlobalBusinessException.Type.INVALID_AMOUNT;
        String message = "Test message";

        // When
        GlobalBusinessException exception = new GlobalBusinessException(type, message);

        // Then - Should be able to catch the specific exception type
        GlobalBusinessException thrownException = assertThrows(GlobalBusinessException.class, () -> {
            throw exception;
        });

        assertEquals(type, thrownException.getType());
        assertEquals(message, thrownException.getMessage());
        assertSame(exception, thrownException);
    }

    @Test
    @DisplayName("Type enum should follow naming convention")
    void typeEnum_ShouldFollowNamingConvention() {
        GlobalBusinessException.Type[] types = GlobalBusinessException.Type.values();

        for (GlobalBusinessException.Type type : types) {
            String name = type.name();
            // Should be all uppercase
            assertEquals(name.toUpperCase(), name,
                    "Type name should be uppercase: " + name);
            // Should use underscores for word separation
            assertFalse(name.contains("-"),
                    "Type name should use underscores, not hyphens: " + name);
            assertFalse(name.contains(" "),
                    "Type name should not contain spaces: " + name);
        }
    }

    @Test
    @DisplayName("Default messages should be in Spanish")
    void defaultMessages_ShouldBeInSpanish() {
        GlobalBusinessException.Type[] types = GlobalBusinessException.Type.values();

        for (GlobalBusinessException.Type type : types) {
            String message = type.defaultMessage;
            // Basic check for Spanish characteristics
            assertTrue(message.contains("El ") || message.contains("La ") || message.contains("Los ") || message.contains("Las "),
                    "Default message should be in Spanish: " + message);
        }
    }

    @Test
    @DisplayName("Should be able to iterate through all types")
    void shouldBeAbleToIterateThroughAllTypes() {
        int count = 0;
        for (GlobalBusinessException.Type type : GlobalBusinessException.Type.values()) {
            assertNotNull(type);
            assertNotNull(type.defaultMessage);

            // Test that build methods work
            GlobalBusinessException exception1 = type.build();
            GlobalBusinessException exception2 = type.build("Custom message");

            assertNotNull(exception1);
            assertNotNull(exception2);
            assertEquals(type, exception1.getType());
            assertEquals(type, exception2.getType());

            count++;
        }
        assertEquals(4, count);
    }

    @Test
    @DisplayName("Type enum should be comparable")
    void typeEnum_ShouldBeComparable() {
        // Test ordinal comparison (based on declaration order)
        assertTrue(GlobalBusinessException.Type.INVALID_AMOUNT.ordinal() <
                GlobalBusinessException.Type.INVALID_TERM.ordinal());

        // Test compareTo
        assertTrue(GlobalBusinessException.Type.INVALID_AMOUNT.compareTo(
                GlobalBusinessException.Type.INVALID_TERM) < 0);
        assertEquals(0, GlobalBusinessException.Type.INVALID_AMOUNT.compareTo(
                GlobalBusinessException.Type.INVALID_AMOUNT));
    }

    @Test
    @DisplayName("Exception should preserve original message format")
    void exception_ShouldPreserveOriginalMessageFormat() {
        // Given
        String messageWithSpecialChars = "Error: monto inválido (€1,000.50)";
        GlobalBusinessException.Type type = GlobalBusinessException.Type.INVALID_AMOUNT;

        // When
        GlobalBusinessException exception = new GlobalBusinessException(type, messageWithSpecialChars);

        // Then
        assertEquals(messageWithSpecialChars, exception.getMessage());
        assertEquals(type, exception.getType());
    }

    @Test
    @DisplayName("Builder pattern should work consistently")
    void builderPattern_ShouldWorkConsistently() {
        // Test all types with both build methods
        GlobalBusinessException.Type[] types = GlobalBusinessException.Type.values();

        for (GlobalBusinessException.Type type : types) {
            // Test build()
            GlobalBusinessException exception1 = type.build();
            assertEquals(type, exception1.getType());
            assertEquals(type.defaultMessage, exception1.getMessage());

            // Test build(customMessage)
            String customMessage = "Custom message for " + type.name();
            GlobalBusinessException exception2 = type.build(customMessage);
            assertEquals(type, exception2.getType());
            assertEquals(customMessage, exception2.getMessage());
        }
    }
}