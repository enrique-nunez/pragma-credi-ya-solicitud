package co.com.pragma.model.common.models;

//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.DisplayName;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Field;
//import java.lang.reflect.Modifier;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DisplayName("GlobalBusinessValidation Tests")
//class GlobalBusinessValidationTest {
//
//    @Test
//    @DisplayName("Should have correct value for EMAIL_REQUIRED")
//    void emailRequired_ShouldHaveCorrectValue() {
//        assertEquals("El correo electrónico es obligatorio", GlobalBusinessValidation.EMAIL_REQUIRED);
//    }
//
//    @Test
//    @DisplayName("Should have correct value for INVALID_EMAIL_FORMAT")
//    void invalidEmailFormat_ShouldHaveCorrectValue() {
//        assertEquals("El correo electrónico no es válido", GlobalBusinessValidation.INVALID_EMAIL_FORMAT);
//    }
//
//    @Test
//    @DisplayName("Should have correct value for AMOUNT_REQUIRED")
//    void amountRequired_ShouldHaveCorrectValue() {
//        assertEquals("El monto es obligatorio", GlobalBusinessValidation.AMOUNT_REQUIRED);
//    }
//
//    @Test
//    @DisplayName("Should have correct value for INVALID_AMOUNT")
//    void invalidAmount_ShouldHaveCorrectValue() {
//        assertEquals("El monto debe ser un número positivo", GlobalBusinessValidation.INVALID_AMOUNT);
//    }
//
//    @Test
//    @DisplayName("Should have correct value for TYPE_REQUIRED")
//    void typeRequired_ShouldHaveCorrectValue() {
//        assertEquals("El tipo de préstamo es obligatorio", GlobalBusinessValidation.TYPE_REQUIRED);
//    }
//
//    @Test
//    @DisplayName("Should have correct value for TERM_REQUIRED")
//    void termRequired_ShouldHaveCorrectValue() {
//        assertEquals("El plazo es obligatorio", GlobalBusinessValidation.TERM_REQUIRED);
//    }
//
//    @Test
//    @DisplayName("Should have correct value for INVALID_TERM")
//    void invalidTerm_ShouldHaveCorrectValue() {
//        assertEquals("El plazo debe ser un número positivo", GlobalBusinessValidation.INVALID_TERM);
//    }
//
//    @Test
//    @DisplayName("Should have correct value for INVALID_BODY_REQUEST")
//    void invalidBodyRequest_ShouldHaveCorrectValue() {
//        assertEquals("El cuerpo de la solicitud no es válido", GlobalBusinessValidation.INVALID_BODY_REQUEST);
//    }
//
//    @Test
//    @DisplayName("Should have correct value for OPERATION_SUCCESSFUL")
//    void operationSuccessful_ShouldHaveCorrectValue() {
//        assertEquals("Operación exitosa", GlobalBusinessValidation.OPERATION_SUCCESSFUL);
//    }
//
//    @Test
//    @DisplayName("Should have correct value for OPERATION_FAILED")
//    void operationFailed_ShouldHaveCorrectValue() {
//        assertEquals("Operación fallida", GlobalBusinessValidation.OPERATION_FAILED);
//    }
//
//    @Test
//    @DisplayName("All constants should be public static final")
//    void allConstants_ShouldBePublicStaticFinal() throws Exception {
//        Field[] fields = GlobalBusinessValidation.class.getDeclaredFields();
//
//        for (Field field : fields) {
//            int modifiers = field.getModifiers();
//            assertTrue(Modifier.isPublic(modifiers),
//                    "Field " + field.getName() + " should be public");
//            assertTrue(Modifier.isStatic(modifiers),
//                    "Field " + field.getName() + " should be static");
//            assertTrue(Modifier.isFinal(modifiers),
//                    "Field " + field.getName() + " should be final");
//        }
//    }
//
//    @Test
//    @DisplayName("All constants should be String type")
//    void allConstants_ShouldBeStringType() throws Exception {
//        Field[] fields = GlobalBusinessValidation.class.getDeclaredFields();
//
//        for (Field field : fields) {
//            assertEquals(String.class, field.getType(),
//                    "Field " + field.getName() + " should be of String type");
//        }
//    }
//
//    @Test
//    @DisplayName("All constants should have non-null values")
//    void allConstants_ShouldHaveNonNullValues() throws Exception {
//        Field[] fields = GlobalBusinessValidation.class.getDeclaredFields();
//
//        for (Field field : fields) {
//            Object value = field.get(null); // null because it's static
//            assertNotNull(value, "Field " + field.getName() + " should not be null");
//        }
//    }
//
//    @Test
//    @DisplayName("All constants should have non-empty values")
//    void allConstants_ShouldHaveNonEmptyValues() throws Exception {
//        Field[] fields = GlobalBusinessValidation.class.getDeclaredFields();
//
//        for (Field field : fields) {
//            String value = (String) field.get(null);
//            assertFalse(value.isEmpty(), "Field " + field.getName() + " should not be empty");
//            assertFalse(value.isBlank(), "Field " + field.getName() + " should not be blank");
//        }
//    }
//
//    @Test
//    @DisplayName("Should have exactly 10 constants defined")
//    void shouldHaveCorrectNumberOfConstants() {
//        Field[] fields = GlobalBusinessValidation.class.getDeclaredFields();
//        assertEquals(10, fields.length);
//    }
//
//    @Test
//    @DisplayName("Validation messages should be in Spanish")
//    void validationMessages_ShouldBeInSpanish() {
//        String[] validationMessages = {
//                GlobalBusinessValidation.EMAIL_REQUIRED,
//                GlobalBusinessValidation.INVALID_EMAIL_FORMAT,
//                GlobalBusinessValidation.AMOUNT_REQUIRED,
//                GlobalBusinessValidation.INVALID_AMOUNT,
//                GlobalBusinessValidation.TYPE_REQUIRED,
//                GlobalBusinessValidation.TERM_REQUIRED,
//                GlobalBusinessValidation.INVALID_TERM,
//                GlobalBusinessValidation.INVALID_BODY_REQUEST
//        };
//
//        for (String message : validationMessages) {
//            // Basic check for Spanish characteristics
//            assertTrue(message.contains("El ") || message.contains("La ") ||
//                            message.contains("es ") || message.contains("debe "),
//                    "Validation message should be in Spanish: " + message);
//        }
//    }
//
//    @Test
//    @DisplayName("Operation messages should be in Spanish")
//    void operationMessages_ShouldBeInSpanish() {
//        assertTrue(GlobalBusinessValidation.OPERATION_SUCCESSFUL.contains("Operación"),
//                "Operation successful message should be in Spanish");
//        assertTrue(GlobalBusinessValidation.OPERATION_FAILED.contains("Operación"),
//                "Operation failed message should be in Spanish");
//    }
//
//    @Test
//    @DisplayName("Constants should follow naming convention")
//    void constants_ShouldFollowNamingConvention() throws Exception {
//        Field[] fields = GlobalBusinessValidation.class.getDeclaredFields();
//
//        for (Field field : fields) {
//            String name = field.getName();
//            // Should be all uppercase
//            assertEquals(name.toUpperCase(), name,
//                    "Constant name should be uppercase: " + name);
//            // Should use underscores for word separation
//            assertFalse(name.contains("-"),
//                    "Constant name should use underscores, not hyphens: " + name);
//            assertFalse(name.contains(" "),
//                    "Constant name should not contain spaces: " + name);
//        }
//    }
//
//    @Test
//    @DisplayName("Should be a utility class")
//    void shouldBeUtilityClass() {
//        // Check if class has only private constructor or no constructor
//        Constructor<?>[] constructors = GlobalBusinessValidation.class.getDeclaredConstructors();
//
//        if (constructors.length > 0) {
//            // If there are constructors, they should be private
//            for (Constructor<?> constructor : constructors) {
//                assertTrue(Modifier.isPrivate(constructor.getModifiers()) ||
//                                Modifier.isPublic(constructor.getModifiers()),
//                        "Utility class constructor accessibility");
//            }
//        }
//    }
//
//    @Test
//    @DisplayName("Class should be public")
//    void class_ShouldBePublic() {
//        assertTrue(Modifier.isPublic(GlobalBusinessValidation.class.getModifiers()),
//                "GlobalBusinessValidation class should be public");
//    }
//
//    @Test
//    @DisplayName("Constants should be accessible without instantiation")
//    void constants_ShouldBeAccessibleWithoutInstantiation() {
//        // Should be able to access constants without creating an instance
//        assertDoesNotThrow(() -> {
//            String email = GlobalBusinessValidation.EMAIL_REQUIRED;
//            String amount = GlobalBusinessValidation.AMOUNT_REQUIRED;
//            String success = GlobalBusinessValidation.OPERATION_SUCCESSFUL;
//
//            assertNotNull(email);
//            assertNotNull(amount);
//            assertNotNull(success);
//        });
//    }
//
//    @Test
//    @DisplayName("Email validation constants should be related to email")
//    void emailValidationConstants_ShouldBeRelatedToEmail() {
//        assertTrue(GlobalBusinessValidation.EMAIL_REQUIRED.toLowerCase().contains("correo"),
//                "EMAIL_REQUIRED should mention email");
//        assertTrue(GlobalBusinessValidation.INVALID_EMAIL_FORMAT.toLowerCase().contains("correo"),
//                "INVALID_EMAIL_FORMAT should mention email");
//    }
//
//    @Test
//    @DisplayName("Amount validation constants should be related to amount")
//    void amountValidationConstants_ShouldBeRelatedToAmount() {
//        assertTrue(GlobalBusinessValidation.AMOUNT_REQUIRED.toLowerCase().contains("monto"),
//                "AMOUNT_REQUIRED should mention amount");
//        assertTrue(GlobalBusinessValidation.INVALID_AMOUNT.toLowerCase().contains("monto"),
//                "INVALID_AMOUNT should mention amount");
//    }
//
//    @Test
//    @DisplayName("Term validation constants should be related to term")
//    void termValidationConstants_ShouldBeRelatedToTerm() {
//        assertTrue(GlobalBusinessValidation.TERM_REQUIRED.toLowerCase().contains("plazo"),
//                "TERM_REQUIRED should mention term");
//        assertTrue(GlobalBusinessValidation.INVALID_TERM.toLowerCase().contains("plazo"),
//                "INVALID_TERM should mention term");
//    }
//
////    @Test
////    @DisplayName("Type validation constant should be related to loan type")
////    void typeValidationConstant_ShouldBeRelatedToLoanType() {
////        String typeRequired = GlobalBusinessValidation.TYPE_REQUIRED.toLowerCase();
////        assertTrue(typeRequired.contains("tipo") && typeRequired.contains("préstamo"),
////                "TYPE_REQUIRED should mention loan type. Actual value: " + GlobalBusinessValidation.TYPE_REQUIRED);
////    }
//
//    @Test
//    @DisplayName("Constants should have consistent message format")
//    void constants_ShouldHaveConsistentMessageFormat() {
//        // Required field messages should follow pattern
//        String[] requiredMessages = {
//                GlobalBusinessValidation.EMAIL_REQUIRED,
//                GlobalBusinessValidation.AMOUNT_REQUIRED,
//                GlobalBusinessValidation.TYPE_REQUIRED,
//                GlobalBusinessValidation.TERM_REQUIRED
//        };
//
//        for (String message : requiredMessages) {
//            assertTrue(message.contains("obligatorio") || message.contains("es obligatorio"),
//                    "Required message should contain 'obligatorio': " + message);
//        }
//
//        // Invalid format messages should follow pattern
//        String[] invalidMessages = {
//                GlobalBusinessValidation.INVALID_EMAIL_FORMAT,
//                GlobalBusinessValidation.INVALID_AMOUNT,
//                GlobalBusinessValidation.INVALID_TERM
//        };
//
//        for (String message : invalidMessages) {
//            assertTrue(message.contains("no es válido") || message.contains("debe ser"),
//                    "Invalid message should indicate invalidity: " + message);
//        }
//    }
//
//    @Test
//    @DisplayName("Operation messages should be opposites")
//    void operationMessages_ShouldBeOpposites() {
//        assertTrue(GlobalBusinessValidation.OPERATION_SUCCESSFUL.contains("exitosa"),
//                "Successful operation should indicate success");
//        assertTrue(GlobalBusinessValidation.OPERATION_FAILED.contains("fallida"),
//                "Failed operation should indicate failure");
//    }
//
//    @Test
//    @DisplayName("Constants should be immutable")
//    void constants_ShouldBeImmutable() throws Exception {
//        Field[] fields = GlobalBusinessValidation.class.getDeclaredFields();
//
//        for (Field field : fields) {
//            assertTrue(Modifier.isFinal(field.getModifiers()),
//                    "Constant " + field.getName() + " should be final (immutable)");
//        }
//    }
//
//    @Test
//    @DisplayName("Should not have any non-constant fields")
//    void shouldNotHaveAnyNonConstantFields() throws Exception {
//        Field[] fields = GlobalBusinessValidation.class.getDeclaredFields();
//
//        for (Field field : fields) {
//            assertTrue(Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()),
//                    "All fields should be static final constants: " + field.getName());
//        }
//    }
//
//    @Test
//    @DisplayName("Constants should be grouped logically")
//    void constants_ShouldBeGroupedLogically() {
//        // Email related constants
//        assertNotNull(GlobalBusinessValidation.EMAIL_REQUIRED);
//        assertNotNull(GlobalBusinessValidation.INVALID_EMAIL_FORMAT);
//
//        // Amount related constants
//        assertNotNull(GlobalBusinessValidation.AMOUNT_REQUIRED);
//        assertNotNull(GlobalBusinessValidation.INVALID_AMOUNT);
//
//        // Term related constants
//        assertNotNull(GlobalBusinessValidation.TERM_REQUIRED);
//        assertNotNull(GlobalBusinessValidation.INVALID_TERM);
//
//        // Type related constant
//        assertNotNull(GlobalBusinessValidation.TYPE_REQUIRED);
//
//        // Request related constant
//        assertNotNull(GlobalBusinessValidation.INVALID_BODY_REQUEST);
//
//        // Operation related constants
//        assertNotNull(GlobalBusinessValidation.OPERATION_SUCCESSFUL);
//        assertNotNull(GlobalBusinessValidation.OPERATION_FAILED);
//    }
//
//    @Test
//    @DisplayName("Should be able to use constants in string operations")
//    void shouldBeAbleToUseConstantsInStringOperations() {
//        // Test concatenation
//        String combined = GlobalBusinessValidation.EMAIL_REQUIRED + " - " + GlobalBusinessValidation.INVALID_EMAIL_FORMAT;
//        assertNotNull(combined);
//        assertTrue(combined.contains("correo"));
//
//        // Test comparison
//        assertNotEquals(GlobalBusinessValidation.OPERATION_SUCCESSFUL, GlobalBusinessValidation.OPERATION_FAILED);
//
//        // Test length
//        assertTrue(GlobalBusinessValidation.EMAIL_REQUIRED.length() > 0);
//
//        // Test contains
//        assertTrue(GlobalBusinessValidation.AMOUNT_REQUIRED.contains("monto"));
//    }
//
//    @Test
//    @DisplayName("Constants should have reasonable length")
//    void constants_ShouldHaveReasonableLength() throws Exception {
//        Field[] fields = GlobalBusinessValidation.class.getDeclaredFields();
//
//        for (Field field : fields) {
//            String value = (String) field.get(null);
//            assertTrue(value.length() >= 10 && value.length() <= 100,
//                    "Constant " + field.getName() + " should have reasonable length (10-100 chars): " + value.length());
//        }
//    }
//
//    @Test
//    @DisplayName("Should work with collections")
//    void shouldWorkWithCollections() {
//        java.util.List<String> validationMessages = java.util.Arrays.asList(
//                GlobalBusinessValidation.EMAIL_REQUIRED,
//                GlobalBusinessValidation.INVALID_EMAIL_FORMAT,
//                GlobalBusinessValidation.AMOUNT_REQUIRED,
//                GlobalBusinessValidation.INVALID_AMOUNT,
//                GlobalBusinessValidation.TYPE_REQUIRED,
//                GlobalBusinessValidation.TERM_REQUIRED,
//                GlobalBusinessValidation.INVALID_TERM,
//                GlobalBusinessValidation.INVALID_BODY_REQUEST
//        );
//
//        assertEquals(8, validationMessages.size());
//        assertTrue(validationMessages.contains(GlobalBusinessValidation.EMAIL_REQUIRED));
//
//        // Test with Set to verify uniqueness
//        java.util.Set<String> uniqueMessages = new java.util.HashSet<>(validationMessages);
//        assertEquals(8, uniqueMessages.size(), "All validation messages should be unique");
//    }
//}