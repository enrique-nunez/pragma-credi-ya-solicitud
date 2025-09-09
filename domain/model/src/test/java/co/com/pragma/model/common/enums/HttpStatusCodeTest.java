package co.com.pragma.model.common.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HttpStatusCode Enum Tests")
class HttpStatusCodeTest {

    @Test
    @DisplayName("Should have correct value for OK")
    void ok_ShouldHaveCorrectValue() {
        HttpStatusCode statusCode = HttpStatusCode.OK;
        assertEquals(200, statusCode.getValue());
    }

    @Test
    @DisplayName("Should have correct value for CREATED")
    void created_ShouldHaveCorrectValue() {
        HttpStatusCode statusCode = HttpStatusCode.CREATED;
        assertEquals(201, statusCode.getValue());
    }

    @Test
    @DisplayName("Should have correct value for BAD_REQUEST")
    void badRequest_ShouldHaveCorrectValue() {
        HttpStatusCode statusCode = HttpStatusCode.BAD_REQUEST;
        assertEquals(400, statusCode.getValue());
    }

    @Test
    @DisplayName("Should have correct value for UNAUTHORIZED")
    void unauthorized_ShouldHaveCorrectValue() {
        HttpStatusCode statusCode = HttpStatusCode.UNAUTHORIZED;
        assertEquals(401, statusCode.getValue());
    }

    @Test
    @DisplayName("Should have correct value for FORBIDDEN")
    void forbidden_ShouldHaveCorrectValue() {
        HttpStatusCode statusCode = HttpStatusCode.FORBIDDEN;
        assertEquals(403, statusCode.getValue());
    }

    @Test
    @DisplayName("Should have correct value for NOT_FOUND")
    void notFound_ShouldHaveCorrectValue() {
        HttpStatusCode statusCode = HttpStatusCode.NOT_FOUND;
        assertEquals(404, statusCode.getValue());
    }

    @Test
    @DisplayName("Should have correct value for INTERNAL_SERVER_ERROR")
    void internalServerError_ShouldHaveCorrectValue() {
        HttpStatusCode statusCode = HttpStatusCode.INTERNAL_SERVER_ERROR;
        assertEquals(500, statusCode.getValue());
    }

    @ParameterizedTest
    @EnumSource(HttpStatusCode.class)
    @DisplayName("All status codes should have positive values")
    void allStatusCodes_ShouldHavePositiveValues(HttpStatusCode statusCode) {
        assertTrue(statusCode.getValue() > 0,
                "Status code should be positive: " + statusCode.getValue());
    }

    @ParameterizedTest
    @EnumSource(HttpStatusCode.class)
    @DisplayName("All status codes should be valid HTTP status codes")
    void allStatusCodes_ShouldBeValidHttpStatusCodes(HttpStatusCode statusCode) {
        int value = statusCode.getValue();
        assertTrue(value >= 100 && value < 600,
                "HTTP status code should be between 100-599: " + value);
    }

    @Test
    @DisplayName("Should have exactly 7 status codes defined")
    void shouldHaveCorrectNumberOfStatusCodes() {
        HttpStatusCode[] statusCodes = HttpStatusCode.values();
        assertEquals(7, statusCodes.length);
    }

    @Test
    @DisplayName("All status codes should have unique values")
    void allStatusCodes_ShouldHaveUniqueValues() {
        HttpStatusCode[] statusCodes = HttpStatusCode.values();

        for (int i = 0; i < statusCodes.length; i++) {
            for (int j = i + 1; j < statusCodes.length; j++) {
                assertNotEquals(statusCodes[i].getValue(), statusCodes[j].getValue(),
                        "Status codes should have unique values: " + statusCodes[i].name() +
                                " and " + statusCodes[j].name() + " both have value: " + statusCodes[i].getValue());
            }
        }
    }

    @Test
    @DisplayName("Success status codes should be in 2xx range")
    void successStatusCodes_ShouldBeIn2xxRange() {
        HttpStatusCode[] successCodes = {
                HttpStatusCode.OK,
                HttpStatusCode.CREATED
        };

        for (HttpStatusCode statusCode : successCodes) {
            int value = statusCode.getValue();
            assertTrue(value >= 200 && value < 300,
                    "Success status code should be in 2xx range: " + value);
        }
    }

    @Test
    @DisplayName("Client error status codes should be in 4xx range")
    void clientErrorStatusCodes_ShouldBeIn4xxRange() {
        HttpStatusCode[] clientErrorCodes = {
                HttpStatusCode.BAD_REQUEST,
                HttpStatusCode.UNAUTHORIZED,
                HttpStatusCode.FORBIDDEN,
                HttpStatusCode.NOT_FOUND
        };

        for (HttpStatusCode statusCode : clientErrorCodes) {
            int value = statusCode.getValue();
            assertTrue(value >= 400 && value < 500,
                    "Client error status code should be in 4xx range: " + value);
        }
    }

    @Test
    @DisplayName("Server error status codes should be in 5xx range")
    void serverErrorStatusCodes_ShouldBeIn5xxRange() {
        HttpStatusCode[] serverErrorCodes = {
                HttpStatusCode.INTERNAL_SERVER_ERROR
        };

        for (HttpStatusCode statusCode : serverErrorCodes) {
            int value = statusCode.getValue();
            assertTrue(value >= 500 && value < 600,
                    "Server error status code should be in 5xx range: " + value);
        }
    }

    @Test
    @DisplayName("Status codes should be accessible by name")
    void statusCodes_ShouldBeAccessibleByName() {
        assertEquals(HttpStatusCode.OK, HttpStatusCode.valueOf("OK"));
        assertEquals(HttpStatusCode.CREATED, HttpStatusCode.valueOf("CREATED"));
        assertEquals(HttpStatusCode.BAD_REQUEST, HttpStatusCode.valueOf("BAD_REQUEST"));
        assertEquals(HttpStatusCode.NOT_FOUND, HttpStatusCode.valueOf("NOT_FOUND"));
        assertEquals(HttpStatusCode.INTERNAL_SERVER_ERROR, HttpStatusCode.valueOf("INTERNAL_SERVER_ERROR"));
        assertEquals(HttpStatusCode.UNAUTHORIZED, HttpStatusCode.valueOf("UNAUTHORIZED"));
        assertEquals(HttpStatusCode.FORBIDDEN, HttpStatusCode.valueOf("FORBIDDEN"));
    }

    @Test
    @DisplayName("Should throw exception for invalid enum name")
    void valueOf_WithInvalidName_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            HttpStatusCode.valueOf("INVALID_STATUS_CODE");
        });
    }

    @Test
    @DisplayName("toString should return enum name")
    void toString_ShouldReturnEnumName() {
        assertEquals("OK", HttpStatusCode.OK.toString());
        assertEquals("CREATED", HttpStatusCode.CREATED.toString());
        assertEquals("BAD_REQUEST", HttpStatusCode.BAD_REQUEST.toString());
        assertEquals("NOT_FOUND", HttpStatusCode.NOT_FOUND.toString());
        assertEquals("INTERNAL_SERVER_ERROR", HttpStatusCode.INTERNAL_SERVER_ERROR.toString());
        assertEquals("UNAUTHORIZED", HttpStatusCode.UNAUTHORIZED.toString());
        assertEquals("FORBIDDEN", HttpStatusCode.FORBIDDEN.toString());
    }

    @Test
    @DisplayName("Status codes should follow naming convention")
    void statusCodes_ShouldFollowNamingConvention() {
        HttpStatusCode[] statusCodes = HttpStatusCode.values();

        for (HttpStatusCode statusCode : statusCodes) {
            String name = statusCode.name();
            // Should be all uppercase
            assertEquals(name.toUpperCase(), name,
                    "Status code name should be uppercase: " + name);
            // Should use underscores for word separation
            assertFalse(name.contains("-"),
                    "Status code name should use underscores, not hyphens: " + name);
            assertFalse(name.contains(" "),
                    "Status code name should not contain spaces: " + name);
        }
    }

    @Test
    @DisplayName("Should be able to iterate through all status codes")
    void shouldBeAbleToIterateThroughAllStatusCodes() {
        int count = 0;
        for (HttpStatusCode statusCode : HttpStatusCode.values()) {
            assertNotNull(statusCode);
            assertTrue(statusCode.getValue() > 0);
            count++;
        }
        assertEquals(7, count);
    }

    @Test
    @DisplayName("Enum should be comparable")
    void enum_ShouldBeComparable() {
        // Test ordinal comparison (based on declaration order)
        assertTrue(HttpStatusCode.OK.ordinal() < HttpStatusCode.CREATED.ordinal());
        assertTrue(HttpStatusCode.FORBIDDEN.ordinal() > HttpStatusCode.OK.ordinal());

        // Test compareTo
        assertTrue(HttpStatusCode.OK.compareTo(HttpStatusCode.CREATED) < 0);
        assertTrue(HttpStatusCode.CREATED.compareTo(HttpStatusCode.OK) > 0);
        assertEquals(0, HttpStatusCode.OK.compareTo(HttpStatusCode.OK));
    }

    @Test
    @DisplayName("Should contain most common HTTP status codes")
    void shouldContainMostCommonHttpStatusCodes() {
        // Verify that the enum contains the most commonly used HTTP status codes
        assertNotNull(HttpStatusCode.valueOf("OK")); // 200
        assertNotNull(HttpStatusCode.valueOf("BAD_REQUEST")); // 400
        assertNotNull(HttpStatusCode.valueOf("NOT_FOUND")); // 404
        assertNotNull(HttpStatusCode.valueOf("INTERNAL_SERVER_ERROR")); // 500
    }

    @Test
    @DisplayName("Status code values should match HTTP standard")
    void statusCodeValues_ShouldMatchHttpStandard() {
        // Verify specific HTTP status code values match the standard
        assertEquals(200, HttpStatusCode.OK.getValue()); // OK
        assertEquals(201, HttpStatusCode.CREATED.getValue()); // Created
        assertEquals(400, HttpStatusCode.BAD_REQUEST.getValue()); // Bad Request
        assertEquals(401, HttpStatusCode.UNAUTHORIZED.getValue()); // Unauthorized
        assertEquals(403, HttpStatusCode.FORBIDDEN.getValue()); // Forbidden
        assertEquals(404, HttpStatusCode.NOT_FOUND.getValue()); // Not Found
        assertEquals(500, HttpStatusCode.INTERNAL_SERVER_ERROR.getValue()); // Internal Server Error
    }

    @Test
    @DisplayName("Should be able to find status code by value")
    void shouldBeAbleToFindStatusCodeByValue() {
        // Helper method test - if you want to add a method to find by value
        HttpStatusCode[] statusCodes = HttpStatusCode.values();

        // Test finding OK (200)
        HttpStatusCode found = null;
        for (HttpStatusCode code : statusCodes) {
            if (code.getValue() == 200) {
                found = code;
                break;
            }
        }
        assertEquals(HttpStatusCode.OK, found);

        // Test finding NOT_FOUND (404)
        found = null;
        for (HttpStatusCode code : statusCodes) {
            if (code.getValue() == 404) {
                found = code;
                break;
            }
        }
        assertEquals(HttpStatusCode.NOT_FOUND, found);
    }

    @Test
    @DisplayName("Should handle edge cases for status code ranges")
    void shouldHandleEdgeCasesForStatusCodeRanges() {
        // Test boundary values
        assertEquals(200, HttpStatusCode.OK.getValue()); // Lower bound of 2xx
        assertEquals(201, HttpStatusCode.CREATED.getValue()); // Another 2xx

        assertEquals(400, HttpStatusCode.BAD_REQUEST.getValue()); // Lower bound of 4xx
        assertEquals(401, HttpStatusCode.UNAUTHORIZED.getValue()); // 4xx
        assertEquals(403, HttpStatusCode.FORBIDDEN.getValue()); // 4xx
        assertEquals(404, HttpStatusCode.NOT_FOUND.getValue()); // 4xx

        assertEquals(500, HttpStatusCode.INTERNAL_SERVER_ERROR.getValue()); // Lower bound of 5xx
    }

    @Test
    @DisplayName("Enum constants should be immutable")
    void enumConstants_ShouldBeImmutable() {
        // Enum constants are inherently immutable in Java
        HttpStatusCode original = HttpStatusCode.OK;
        HttpStatusCode same = HttpStatusCode.OK;

        assertSame(original, same); // Same instance
        assertEquals(original.getValue(), same.getValue());
    }

    @Test
    @DisplayName("Should maintain consistent ordering")
    void shouldMaintainConsistentOrdering() {
        HttpStatusCode[] statusCodes = HttpStatusCode.values();

        // Verify the order matches declaration order
        assertEquals(HttpStatusCode.OK, statusCodes[0]);
        assertEquals(HttpStatusCode.CREATED, statusCodes[1]);
        assertEquals(HttpStatusCode.BAD_REQUEST, statusCodes[2]);
        assertEquals(HttpStatusCode.NOT_FOUND, statusCodes[3]);
        assertEquals(HttpStatusCode.INTERNAL_SERVER_ERROR, statusCodes[4]);
        assertEquals(HttpStatusCode.UNAUTHORIZED, statusCodes[5]);
        assertEquals(HttpStatusCode.FORBIDDEN, statusCodes[6]);
    }
}