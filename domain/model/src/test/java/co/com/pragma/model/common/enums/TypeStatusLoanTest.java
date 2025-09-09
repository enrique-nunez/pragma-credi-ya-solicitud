package co.com.pragma.model.common.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TypeStatusLoan Enum Tests")
class TypeStatusLoanTest {

    @Test
    @DisplayName("Should have correct value and description for PENDING")
    void pending_ShouldHaveCorrectValueAndDescription() {
        TypeStatusLoan statusLoan = TypeStatusLoan.PENDING;

        assertEquals(1L, statusLoan.getValue());
        assertEquals("Pendiente de revisión", statusLoan.getDescription());
    }

    @Test
    @DisplayName("Should have correct value and description for APPROVED")
    void approved_ShouldHaveCorrectValueAndDescription() {
        TypeStatusLoan statusLoan = TypeStatusLoan.APPROVED;

        assertEquals(2L, statusLoan.getValue());
        assertEquals("El préstamo ha sido aprobado", statusLoan.getDescription());
    }

    @Test
    @DisplayName("Should have correct value and description for REJECTED")
    void rejected_ShouldHaveCorrectValueAndDescription() {
        TypeStatusLoan statusLoan = TypeStatusLoan.REJECTED;

        assertEquals(3L, statusLoan.getValue());
        assertEquals("El préstamo ha sido rechazado", statusLoan.getDescription());
    }

    @ParameterizedTest
    @EnumSource(TypeStatusLoan.class)
    @DisplayName("All status loans should have positive values")
    void allStatusLoans_ShouldHavePositiveValues(TypeStatusLoan statusLoan) {
        assertTrue(statusLoan.getValue() > 0,
                "Status loan value should be positive: " + statusLoan.getValue());
    }

    @ParameterizedTest
    @EnumSource(TypeStatusLoan.class)
    @DisplayName("All status loans should have non-null and non-empty descriptions")
    void allStatusLoans_ShouldHaveNonNullAndNonEmptyDescriptions(TypeStatusLoan statusLoan) {
        assertNotNull(statusLoan.getDescription());
        assertFalse(statusLoan.getDescription().isEmpty());
        assertFalse(statusLoan.getDescription().isBlank());
    }

    @Test
    @DisplayName("Should have exactly 3 status loan types defined")
    void shouldHaveCorrectNumberOfStatusLoanTypes() {
        TypeStatusLoan[] statusLoans = TypeStatusLoan.values();
        assertEquals(3, statusLoans.length);
    }

    @Test
    @DisplayName("All status loans should have unique values")
    void allStatusLoans_ShouldHaveUniqueValues() {
        TypeStatusLoan[] statusLoans = TypeStatusLoan.values();

        for (int i = 0; i < statusLoans.length; i++) {
            for (int j = i + 1; j < statusLoans.length; j++) {
                assertNotEquals(statusLoans[i].getValue(), statusLoans[j].getValue(),
                        "Status loan values should be unique: " + statusLoans[i].name() +
                                " and " + statusLoans[j].name() + " both have value: " + statusLoans[i].getValue());
            }
        }
    }

    @Test
    @DisplayName("Status loan values should be sequential")
    void statusLoanValues_ShouldBeSequential() {
        assertEquals(1L, TypeStatusLoan.PENDING.getValue());
        assertEquals(2L, TypeStatusLoan.APPROVED.getValue());
        assertEquals(3L, TypeStatusLoan.REJECTED.getValue());
    }

    @Test
    @DisplayName("Status loans should be accessible by name")
    void statusLoans_ShouldBeAccessibleByName() {
        assertEquals(TypeStatusLoan.PENDING, TypeStatusLoan.valueOf("PENDING"));
        assertEquals(TypeStatusLoan.APPROVED, TypeStatusLoan.valueOf("APPROVED"));
        assertEquals(TypeStatusLoan.REJECTED, TypeStatusLoan.valueOf("REJECTED"));
    }

    @Test
    @DisplayName("Should throw exception for invalid enum name")
    void valueOf_WithInvalidName_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            TypeStatusLoan.valueOf("INVALID_STATUS");
        });
    }

    @Test
    @DisplayName("toString should return enum name")
    void toString_ShouldReturnEnumName() {
        assertEquals("PENDING", TypeStatusLoan.PENDING.toString());
        assertEquals("APPROVED", TypeStatusLoan.APPROVED.toString());
        assertEquals("REJECTED", TypeStatusLoan.REJECTED.toString());
    }

    @Test
    @DisplayName("Status loan names should follow naming convention")
    void statusLoanNames_ShouldFollowNamingConvention() {
        TypeStatusLoan[] statusLoans = TypeStatusLoan.values();

        for (TypeStatusLoan statusLoan : statusLoans) {
            String name = statusLoan.name();
            // Should be all uppercase
            assertEquals(name.toUpperCase(), name,
                    "Status loan name should be uppercase: " + name);
            // Should not contain spaces or hyphens
            assertFalse(name.contains(" "),
                    "Status loan name should not contain spaces: " + name);
            assertFalse(name.contains("-"),
                    "Status loan name should not contain hyphens: " + name);
        }
    }

    @Test
    @DisplayName("Should be able to iterate through all status loans")
    void shouldBeAbleToIterateThroughAllStatusLoans() {
        int count = 0;
        for (TypeStatusLoan statusLoan : TypeStatusLoan.values()) {
            assertNotNull(statusLoan);
            assertNotNull(statusLoan.getValue());
            assertNotNull(statusLoan.getDescription());
            count++;
        }
        assertEquals(3, count);
    }

    @Test
    @DisplayName("Enum should be comparable")
    void enum_ShouldBeComparable() {
        // Test ordinal comparison (based on declaration order)
        assertTrue(TypeStatusLoan.PENDING.ordinal() < TypeStatusLoan.APPROVED.ordinal());
        assertTrue(TypeStatusLoan.APPROVED.ordinal() < TypeStatusLoan.REJECTED.ordinal());

        // Test compareTo
        assertTrue(TypeStatusLoan.PENDING.compareTo(TypeStatusLoan.APPROVED) < 0);
        assertTrue(TypeStatusLoan.APPROVED.compareTo(TypeStatusLoan.REJECTED) < 0);
        assertTrue(TypeStatusLoan.REJECTED.compareTo(TypeStatusLoan.PENDING) > 0);
        assertEquals(0, TypeStatusLoan.PENDING.compareTo(TypeStatusLoan.PENDING));
    }

    @Test
    @DisplayName("Should represent loan workflow states")
    void shouldRepresentLoanWorkflowStates() {
        // Verify that the enum represents a logical workflow
        assertEquals(1L, TypeStatusLoan.PENDING.getValue()); // Initial state
        assertEquals(2L, TypeStatusLoan.APPROVED.getValue()); // Positive outcome
        assertEquals(3L, TypeStatusLoan.REJECTED.getValue()); // Negative outcome
    }

    @Test
    @DisplayName("Descriptions should be in Spanish")
    void descriptions_ShouldBeInSpanish() {
        // PENDING description
        String pendingDesc = TypeStatusLoan.PENDING.getDescription();
        assertTrue(pendingDesc.contains("Pendiente") || pendingDesc.contains("revisión"),
                "PENDING description should be in Spanish: " + pendingDesc);

        // APPROVED description
        String approvedDesc = TypeStatusLoan.APPROVED.getDescription();
        assertTrue(approvedDesc.contains("préstamo") && approvedDesc.contains("aprobado"),
                "APPROVED description should be in Spanish: " + approvedDesc);

        // REJECTED description
        String rejectedDesc = TypeStatusLoan.REJECTED.getDescription();
        assertTrue(rejectedDesc.contains("préstamo") && rejectedDesc.contains("rechazado"),
                "REJECTED description should be in Spanish: " + rejectedDesc);
    }

    @Test
    @DisplayName("Should be able to find status by value")
    void shouldBeAbleToFindStatusByValue() {
        TypeStatusLoan[] statusLoans = TypeStatusLoan.values();

        // Test finding PENDING (1L)
        TypeStatusLoan found = null;
        for (TypeStatusLoan status : statusLoans) {
            if (status.getValue().equals(1L)) {
                found = status;
                break;
            }
        }
        assertEquals(TypeStatusLoan.PENDING, found);

        // Test finding APPROVED (2L)
        found = null;
        for (TypeStatusLoan status : statusLoans) {
            if (status.getValue().equals(2L)) {
                found = status;
                break;
            }
        }
        assertEquals(TypeStatusLoan.APPROVED, found);

        // Test finding REJECTED (3L)
        found = null;
        for (TypeStatusLoan status : statusLoans) {
            if (status.getValue().equals(3L)) {
                found = status;
                break;
            }
        }
        assertEquals(TypeStatusLoan.REJECTED, found);
    }

    @Test
    @DisplayName("Should handle null value search gracefully")
    void shouldHandleNullValueSearchGracefully() {
        TypeStatusLoan[] statusLoans = TypeStatusLoan.values();

        TypeStatusLoan found = null;
        for (TypeStatusLoan status : statusLoans) {
            if (status.getValue() == null) {
                found = status;
                break;
            }
        }
        assertNull(found, "No status should have null value");
    }

    @Test
    @DisplayName("Values should be Long type")
    void values_ShouldBeLongType() {
        for (TypeStatusLoan statusLoan : TypeStatusLoan.values()) {
            assertInstanceOf(Long.class, statusLoan.getValue());
        }
    }

    @Test
    @DisplayName("Descriptions should be String type")
    void descriptions_ShouldBeStringType() {
        for (TypeStatusLoan statusLoan : TypeStatusLoan.values()) {
            assertInstanceOf(String.class, statusLoan.getDescription());
        }
    }

    @Test
    @DisplayName("Should maintain consistent ordering")
    void shouldMaintainConsistentOrdering() {
        TypeStatusLoan[] statusLoans = TypeStatusLoan.values();

        // Verify the order matches declaration order
        assertEquals(TypeStatusLoan.PENDING, statusLoans[0]);
        assertEquals(TypeStatusLoan.APPROVED, statusLoans[1]);
        assertEquals(TypeStatusLoan.REJECTED, statusLoans[2]);
    }

    @Test
    @DisplayName("Should represent complete loan decision states")
    void shouldRepresentCompleteLoanDecisionStates() {
        // Verify we have all necessary states for loan processing
        boolean hasPendingState = false;
        boolean hasApprovedState = false;
        boolean hasRejectedState = false;

        for (TypeStatusLoan status : TypeStatusLoan.values()) {
            switch (status) {
                case PENDING -> hasPendingState = true;
                case APPROVED -> hasApprovedState = true;
                case REJECTED -> hasRejectedState = true;
            }
        }

        assertTrue(hasPendingState, "Should have a pending state");
        assertTrue(hasApprovedState, "Should have an approved state");
        assertTrue(hasRejectedState, "Should have a rejected state");
    }

    @Test
    @DisplayName("Enum constants should be immutable")
    void enumConstants_ShouldBeImmutable() {
        // Enum constants are inherently immutable in Java
        TypeStatusLoan original = TypeStatusLoan.PENDING;
        TypeStatusLoan same = TypeStatusLoan.PENDING;

        assertSame(original, same); // Same instance
        assertEquals(original.getValue(), same.getValue());
        assertEquals(original.getDescription(), same.getDescription());
    }

    @Test
    @DisplayName("Should support switch statements")
    void shouldSupportSwitchStatements() {
        // Test that enum works properly in switch statements
        for (TypeStatusLoan status : TypeStatusLoan.values()) {
            String result = switch (status) {
                case PENDING -> "En proceso";
                case APPROVED -> "Aceptado";
                case REJECTED -> "Denegado";
            };

            assertNotNull(result);
            assertFalse(result.isEmpty());
        }
    }

    @Test
    @DisplayName("Should handle edge cases for value comparison")
    void shouldHandleEdgeCasesForValueComparison() {
        // Test boundary values
        assertEquals(1L, TypeStatusLoan.PENDING.getValue()); // Minimum value
        assertEquals(3L, TypeStatusLoan.REJECTED.getValue()); // Maximum value

        // Test that values are within expected range
        for (TypeStatusLoan status : TypeStatusLoan.values()) {
            Long value = status.getValue();
            assertTrue(value >= 1L && value <= 3L,
                    "Status value should be between 1 and 3: " + value);
        }
    }

    @Test
    @DisplayName("Should have meaningful descriptions")
    void shouldHaveMeaningfulDescriptions() {
        // Verify descriptions are meaningful and not just placeholders
        for (TypeStatusLoan status : TypeStatusLoan.values()) {
            String description = status.getDescription();

            // Should be longer than just a single word
            assertTrue(description.length() > 5,
                    "Description should be meaningful: " + description);

            // Should contain relevant keywords
            assertTrue(description.contains("préstamo") ||
                            description.contains("Pendiente") ||
                            description.contains("revisión"),
                    "Description should contain relevant keywords: " + description);
        }
    }

    @Test
    @DisplayName("Should work with collections")
    void shouldWorkWithCollections() {
        // Test that enum works properly with collections
        java.util.List<TypeStatusLoan> statusList = java.util.Arrays.asList(TypeStatusLoan.values());

        assertEquals(3, statusList.size());
        assertTrue(statusList.contains(TypeStatusLoan.PENDING));
        assertTrue(statusList.contains(TypeStatusLoan.APPROVED));
        assertTrue(statusList.contains(TypeStatusLoan.REJECTED));

        // Test with Set to verify uniqueness
        java.util.Set<TypeStatusLoan> statusSet = java.util.EnumSet.allOf(TypeStatusLoan.class);
        assertEquals(3, statusSet.size());
    }
}