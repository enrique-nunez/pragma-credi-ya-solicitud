package co.com.pragma.model.loanapplication.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LoanApplicationPagedResponse Tests")
public class LoanApplicationPagedResponseTest {
    private LoanApplicationPagedResponse testResponse;

    @BeforeEach
    void setUp() {
        testResponse = LoanApplicationPagedResponse.builder()
                .idSolicitud(1L)
                .montoSolicitado(new BigDecimal("15000.00"))
                .plazoMeses(24)
                .emailUsuario("usuario@example.com")
                .tipoPrestamo("Personal")
                .tasaInteres(new BigDecimal("12.5"))
                .estadoSolicitud("APROBADO")
                .build();
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Default constructor should create empty response")
        void defaultConstructor_ShouldCreateEmptyResponse() {
            LoanApplicationPagedResponse response = new LoanApplicationPagedResponse();

            assertNull(response.getIdSolicitud());
            assertNull(response.getMontoSolicitado());
            assertNull(response.getPlazoMeses());
            assertNull(response.getEmailUsuario());
            assertNull(response.getTipoPrestamo());
            assertNull(response.getTasaInteres());
            assertNull(response.getEstadoSolicitud());
        }

        @Test
        @DisplayName("Constructor with all parameters should set all fields correctly")
        void constructorWithAllParameters_ShouldSetAllFields() {
            LoanApplicationPagedResponse response = new LoanApplicationPagedResponse(
                    10L,
                    new BigDecimal("25000.00"),
                    36,
                    "test@example.com",
                    "Hipotecario",
                    new BigDecimal("8.75"),
                    "PENDIENTE",
                    BigDecimal.ZERO
            );

            assertEquals(10L, response.getIdSolicitud());
            assertEquals(new BigDecimal("25000.00"), response.getMontoSolicitado());
            assertEquals(36, response.getPlazoMeses());
            assertEquals("test@example.com", response.getEmailUsuario());
            assertEquals("Hipotecario", response.getTipoPrestamo());
            assertEquals(new BigDecimal("8.75"), response.getTasaInteres());
            assertEquals("PENDIENTE", response.getEstadoSolicitud());
        }

        @Test
        @DisplayName("Constructor with null values should accept nulls")
        void constructorWithNullValues_ShouldAcceptNulls() {
            LoanApplicationPagedResponse response = new LoanApplicationPagedResponse(
                    null, null, null, null, null, null, null, BigDecimal.ZERO
            );

            assertNull(response.getIdSolicitud());
            assertNull(response.getMontoSolicitado());
            assertNull(response.getPlazoMeses());
            assertNull(response.getEmailUsuario());
            assertNull(response.getTipoPrestamo());
            assertNull(response.getTasaInteres());
            assertNull(response.getEstadoSolicitud());
        }
    }

    @Nested
    @DisplayName("Getter Tests")
    class GetterTests {

        @Test
        @DisplayName("getIdSolicitud should return correct value")
        void getIdSolicitud_ShouldReturnCorrectValue() {
            assertEquals(1L, testResponse.getIdSolicitud());
        }

        @Test
        @DisplayName("getMontoSolicitado should return correct amount")
        void getMontoSolicitado_ShouldReturnCorrectAmount() {
            assertEquals(new BigDecimal("15000.00"), testResponse.getMontoSolicitado());
        }

        @Test
        @DisplayName("getPlazoMeses should return correct term")
        void getPlazoMeses_ShouldReturnCorrectTerm() {
            assertEquals(24, testResponse.getPlazoMeses());
        }

        @Test
        @DisplayName("getEmailUsuario should return correct email")
        void getEmailUsuario_ShouldReturnCorrectEmail() {
            assertEquals("usuario@example.com", testResponse.getEmailUsuario());
        }

        @Test
        @DisplayName("getTipoPrestamo should return correct loan type")
        void getTipoPrestamo_ShouldReturnCorrectLoanType() {
            assertEquals("Personal", testResponse.getTipoPrestamo());
        }

        @Test
        @DisplayName("getTasaInteres should return correct interest rate")
        void getTasaInteres_ShouldReturnCorrectInterestRate() {
            assertEquals(new BigDecimal("12.5"), testResponse.getTasaInteres());
        }

        @Test
        @DisplayName("getEstadoSolicitud should return correct status")
        void getEstadoSolicitud_ShouldReturnCorrectStatus() {
            assertEquals("APROBADO", testResponse.getEstadoSolicitud());
        }
    }

    @Nested
    @DisplayName("Setter Tests")
    class SetterTests {

        @Test
        @DisplayName("setIdSolicitud should update field correctly")
        void setIdSolicitud_ShouldUpdateFieldCorrectly() {
            testResponse.setIdSolicitud(999L);
            assertEquals(999L, testResponse.getIdSolicitud());
        }

        @Test
        @DisplayName("setMontoSolicitado should update amount correctly")
        void setMontoSolicitado_ShouldUpdateAmountCorrectly() {
            BigDecimal newAmount = new BigDecimal("50000.00");
            testResponse.setMontoSolicitado(newAmount);
            assertEquals(newAmount, testResponse.getMontoSolicitado());
        }

        @Test
        @DisplayName("setPlazoMeses should update term correctly")
        void setPlazoMeses_ShouldUpdateTermCorrectly() {
            testResponse.setPlazoMeses(48);
            assertEquals(48, testResponse.getPlazoMeses());
        }

        @Test
        @DisplayName("setEmailUsuario should update email correctly")
        void setEmailUsuario_ShouldUpdateEmailCorrectly() {
            testResponse.setEmailUsuario("nuevo@example.com");
            assertEquals("nuevo@example.com", testResponse.getEmailUsuario());
        }

        @Test
        @DisplayName("setTipoPrestamo should update loan type correctly")
        void setTipoPrestamo_ShouldUpdateLoanTypeCorrectly() {
            testResponse.setTipoPrestamo("Vehicular");
            assertEquals("Vehicular", testResponse.getTipoPrestamo());
        }

        @Test
        @DisplayName("setTasaInteres should update interest rate correctly")
        void setTasaInteres_ShouldUpdateInterestRateCorrectly() {
            BigDecimal newRate = new BigDecimal("15.25");
            testResponse.setTasaInteres(newRate);
            assertEquals(newRate, testResponse.getTasaInteres());
        }

        @Test
        @DisplayName("setEstadoSolicitud should update status correctly")
        void setEstadoSolicitud_ShouldUpdateStatusCorrectly() {
            testResponse.setEstadoSolicitud("RECHAZADO");
            assertEquals("RECHAZADO", testResponse.getEstadoSolicitud());
        }

        @Test
        @DisplayName("All setters should accept null values")
        void allSetters_ShouldAcceptNullValues() {
            testResponse.setIdSolicitud(null);
            testResponse.setMontoSolicitado(null);
            testResponse.setPlazoMeses(null);
            testResponse.setEmailUsuario(null);
            testResponse.setTipoPrestamo(null);
            testResponse.setTasaInteres(null);
            testResponse.setEstadoSolicitud(null);

            assertNull(testResponse.getIdSolicitud());
            assertNull(testResponse.getMontoSolicitado());
            assertNull(testResponse.getPlazoMeses());
            assertNull(testResponse.getEmailUsuario());
            assertNull(testResponse.getTipoPrestamo());
            assertNull(testResponse.getTasaInteres());
            assertNull(testResponse.getEstadoSolicitud());
        }
    }

    @Nested
    @DisplayName("Builder Tests")
    class BuilderTests {

        @Test
        @DisplayName("Builder with all fields should create complete response")
        void builderWithAllFields_ShouldCreateCompleteResponse() {
            LoanApplicationPagedResponse response = LoanApplicationPagedResponse.builder()
                    .idSolicitud(5L)
                    .montoSolicitado(new BigDecimal("30000.00"))
                    .plazoMeses(60)
                    .emailUsuario("builder@example.com")
                    .tipoPrestamo("Empresarial")
                    .tasaInteres(new BigDecimal("18.5"))
                    .estadoSolicitud("EN_REVISION")
                    .build();

            assertEquals(5L, response.getIdSolicitud());
            assertEquals(new BigDecimal("30000.00"), response.getMontoSolicitado());
            assertEquals(60, response.getPlazoMeses());
            assertEquals("builder@example.com", response.getEmailUsuario());
            assertEquals("Empresarial", response.getTipoPrestamo());
            assertEquals(new BigDecimal("18.5"), response.getTasaInteres());
            assertEquals("EN_REVISION", response.getEstadoSolicitud());
        }

        @Test
        @DisplayName("Builder with partial fields should create response with nulls")
        void builderWithPartialFields_ShouldCreateResponseWithNulls() {
            LoanApplicationPagedResponse response = LoanApplicationPagedResponse.builder()
                    .idSolicitud(3L)
                    .emailUsuario("partial@example.com")
                    .estadoSolicitud("ACTIVO")
                    .build();

            assertEquals(3L, response.getIdSolicitud());
            assertNull(response.getMontoSolicitado());
            assertNull(response.getPlazoMeses());
            assertEquals("partial@example.com", response.getEmailUsuario());
            assertNull(response.getTipoPrestamo());
            assertNull(response.getTasaInteres());
            assertEquals("ACTIVO", response.getEstadoSolicitud());
        }

        @Test
        @DisplayName("Builder with zero values should accept zero values")
        void builderWithZeroValues_ShouldAcceptZeroValues() {
            LoanApplicationPagedResponse response = LoanApplicationPagedResponse.builder()
                    .idSolicitud(0L)
                    .montoSolicitado(BigDecimal.ZERO)
                    .plazoMeses(0)
                    .tasaInteres(BigDecimal.ZERO)
                    .build();

            assertEquals(0L, response.getIdSolicitud());
            assertEquals(BigDecimal.ZERO, response.getMontoSolicitado());
            assertEquals(0, response.getPlazoMeses());
            assertEquals(BigDecimal.ZERO, response.getTasaInteres());
        }

        @Test
        @DisplayName("Builder with negative values should accept negative values")
        void builderWithNegativeValues_ShouldAcceptNegativeValues() {
            LoanApplicationPagedResponse response = LoanApplicationPagedResponse.builder()
                    .montoSolicitado(new BigDecimal("-1000"))
                    .plazoMeses(-12)
                    .tasaInteres(new BigDecimal("-5.5"))
                    .build();

            assertEquals(new BigDecimal("-1000"), response.getMontoSolicitado());
            assertEquals(-12, response.getPlazoMeses());
            assertEquals(new BigDecimal("-5.5"), response.getTasaInteres());
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "   ", "invalid-email", "test@", "@example.com"})
        @NullSource
        @DisplayName("Builder should accept various email formats")
        void builderWithVariousEmails_ShouldAcceptAllFormats(String email) {
            LoanApplicationPagedResponse response = LoanApplicationPagedResponse.builder()
                    .emailUsuario(email)
                    .build();

            assertEquals(email, response.getEmailUsuario());
        }
    }

    @Nested
    @DisplayName("ToBuilder Tests")
    class ToBuilderTests {

        @Test
        @DisplayName("toBuilder should create builder with same values")
        void toBuilder_ShouldCreateBuilderWithSameValues() {
            LoanApplicationPagedResponse copiedResponse = testResponse.toBuilder().build();

            assertEquals(testResponse.getIdSolicitud(), copiedResponse.getIdSolicitud());
            assertEquals(testResponse.getMontoSolicitado(), copiedResponse.getMontoSolicitado());
            assertEquals(testResponse.getPlazoMeses(), copiedResponse.getPlazoMeses());
            assertEquals(testResponse.getEmailUsuario(), copiedResponse.getEmailUsuario());
            assertEquals(testResponse.getTipoPrestamo(), copiedResponse.getTipoPrestamo());
            assertEquals(testResponse.getTasaInteres(), copiedResponse.getTasaInteres());
            assertEquals(testResponse.getEstadoSolicitud(), copiedResponse.getEstadoSolicitud());
        }

        @Test
        @DisplayName("toBuilder with single field modification should create new response")
        void toBuilderWithSingleModification_ShouldCreateNewResponse() {
            LoanApplicationPagedResponse modifiedResponse = testResponse.toBuilder()
                    .montoSolicitado(new BigDecimal("20000.00"))
                    .build();

            assertEquals(new BigDecimal("20000.00"), modifiedResponse.getMontoSolicitado());
            assertEquals(testResponse.getIdSolicitud(), modifiedResponse.getIdSolicitud());
            assertEquals(testResponse.getPlazoMeses(), modifiedResponse.getPlazoMeses());
            assertEquals(testResponse.getEmailUsuario(), modifiedResponse.getEmailUsuario());
            assertEquals(testResponse.getTipoPrestamo(), modifiedResponse.getTipoPrestamo());
            assertEquals(testResponse.getTasaInteres(), modifiedResponse.getTasaInteres());
            assertEquals(testResponse.getEstadoSolicitud(), modifiedResponse.getEstadoSolicitud());
        }

        @Test
        @DisplayName("toBuilder with multiple modifications should create new response with all changes")
        void toBuilderWithMultipleModifications_ShouldCreateNewResponseWithAllChanges() {
            LoanApplicationPagedResponse modifiedResponse = testResponse.toBuilder()
                    .montoSolicitado(new BigDecimal("35000.00"))
                    .plazoMeses(72)
                    .emailUsuario("modified@example.com")
                    .tipoPrestamo("Hipotecario")
                    .tasaInteres(new BigDecimal("9.25"))
                    .estadoSolicitud("MODIFICADO")
                    .build();

            assertEquals(new BigDecimal("35000.00"), modifiedResponse.getMontoSolicitado());
            assertEquals(72, modifiedResponse.getPlazoMeses());
            assertEquals("modified@example.com", modifiedResponse.getEmailUsuario());
            assertEquals("Hipotecario", modifiedResponse.getTipoPrestamo());
            assertEquals(new BigDecimal("9.25"), modifiedResponse.getTasaInteres());
            assertEquals("MODIFICADO", modifiedResponse.getEstadoSolicitud());

            // Original ID should remain unchanged
            assertEquals(testResponse.getIdSolicitud(), modifiedResponse.getIdSolicitud());
        }

        @Test
        @DisplayName("toBuilder should not affect original object")
        void toBuilder_ShouldNotAffectOriginalObject() {
            // Store original values
            Long originalId = testResponse.getIdSolicitud();
            BigDecimal originalAmount = testResponse.getMontoSolicitado();
            Integer originalTerm = testResponse.getPlazoMeses();
            String originalEmail = testResponse.getEmailUsuario();
            String originalLoanType = testResponse.getTipoPrestamo();
            BigDecimal originalRate = testResponse.getTasaInteres();
            String originalStatus = testResponse.getEstadoSolicitud();

            // Create modified copy
            testResponse.toBuilder()
                    .montoSolicitado(new BigDecimal("99999.99"))
                    .plazoMeses(999)
                    .emailUsuario("changed@example.com")
                    .build();

            // Verify original object remains unchanged
            assertEquals(originalId, testResponse.getIdSolicitud());
            assertEquals(originalAmount, testResponse.getMontoSolicitado());
            assertEquals(originalTerm, testResponse.getPlazoMeses());
            assertEquals(originalEmail, testResponse.getEmailUsuario());
            assertEquals(originalLoanType, testResponse.getTipoPrestamo());
            assertEquals(originalRate, testResponse.getTasaInteres());
            assertEquals(originalStatus, testResponse.getEstadoSolicitud());
        }
    }

    @Nested
    @DisplayName("Edge Cases and Boundary Tests")
    class EdgeCasesTests {

        @Test
        @DisplayName("Builder with empty strings should accept empty values")
        void builderWithEmptyStrings_ShouldAcceptEmptyValues() {
            LoanApplicationPagedResponse response = LoanApplicationPagedResponse.builder()
                    .emailUsuario("")
                    .tipoPrestamo("")
                    .estadoSolicitud("")
                    .build();

            assertEquals("", response.getEmailUsuario());
            assertEquals("", response.getTipoPrestamo());
            assertEquals("", response.getEstadoSolicitud());
        }

        @Test
        @DisplayName("Builder with whitespace strings should preserve whitespace")
        void builderWithWhitespaceStrings_ShouldPreserveWhitespace() {
            LoanApplicationPagedResponse response = LoanApplicationPagedResponse.builder()
                    .emailUsuario("   ")
                    .tipoPrestamo("\t")
                    .estadoSolicitud("\n")
                    .build();

            assertEquals("   ", response.getEmailUsuario());
            assertEquals("\t", response.getTipoPrestamo());
            assertEquals("\n", response.getEstadoSolicitud());
        }

        @Test
        @DisplayName("Builder with very large numbers should handle large values")
        void builderWithLargeNumbers_ShouldHandleLargeValues() {
            BigDecimal largeAmount = new BigDecimal("999999999999.99");
            BigDecimal largeRate = new BigDecimal("999.99");
            Integer largeTerm = Integer.MAX_VALUE;
            Long largeId = Long.MAX_VALUE;

            LoanApplicationPagedResponse response = LoanApplicationPagedResponse.builder()
                    .idSolicitud(largeId)
                    .montoSolicitado(largeAmount)
                    .plazoMeses(largeTerm)
                    .tasaInteres(largeRate)
                    .build();

            assertEquals(largeId, response.getIdSolicitud());
            assertEquals(largeAmount, response.getMontoSolicitado());
            assertEquals(largeTerm, response.getPlazoMeses());
            assertEquals(largeRate, response.getTasaInteres());
        }

        @Test
        @DisplayName("Builder with minimum values should handle minimum values")
        void builderWithMinimumValues_ShouldHandleMinimumValues() {
            Long minId = Long.MIN_VALUE;
            Integer minTerm = Integer.MIN_VALUE;

            LoanApplicationPagedResponse response = LoanApplicationPagedResponse.builder()
                    .idSolicitud(minId)
                    .plazoMeses(minTerm)
                    .build();

            assertEquals(minId, response.getIdSolicitud());
            assertEquals(minTerm, response.getPlazoMeses());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "PENDIENTE", "APROBADO", "RECHAZADO", "EN_REVISION",
                "CANCELADO", "DESEMBOLSADO", "FINALIZADO"
        })
        @DisplayName("Builder should accept various loan statuses")
        void builderWithVariousStatuses_ShouldAcceptAllStatuses(String status) {
            LoanApplicationPagedResponse response = LoanApplicationPagedResponse.builder()
                    .estadoSolicitud(status)
                    .build();

            assertEquals(status, response.getEstadoSolicitud());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "Personal", "Hipotecario", "Vehicular", "Empresarial",
                "Educativo", "Libre Inversión", "Microcrédito"
        })
        @DisplayName("Builder should accept various loan types")
        void builderWithVariousLoanTypes_ShouldAcceptAllTypes(String loanType) {
            LoanApplicationPagedResponse response = LoanApplicationPagedResponse.builder()
                    .tipoPrestamo(loanType)
                    .build();

            assertEquals(loanType, response.getTipoPrestamo());
        }
    }

    @Nested
    @DisplayName("BigDecimal Precision Tests")
    class BigDecimalPrecisionTests {

        @Test
        @DisplayName("Builder should preserve BigDecimal precision for amounts")
        void builderShouldPreserveBigDecimalPrecisionForAmounts() {
            BigDecimal preciseAmount = new BigDecimal("12345.6789");

            LoanApplicationPagedResponse response = LoanApplicationPagedResponse.builder()
                    .montoSolicitado(preciseAmount)
                    .build();

            assertEquals(preciseAmount, response.getMontoSolicitado());
            assertEquals(preciseAmount.scale(), response.getMontoSolicitado().scale());
        }

        @Test
        @DisplayName("Builder should preserve BigDecimal precision for interest rates")
        void builderShouldPreserveBigDecimalPrecisionForRates() {
            BigDecimal preciseRate = new BigDecimal("12.345678");

            LoanApplicationPagedResponse response = LoanApplicationPagedResponse.builder()
                    .tasaInteres(preciseRate)
                    .build();

            assertEquals(preciseRate, response.getTasaInteres());
            assertEquals(preciseRate.scale(), response.getTasaInteres().scale());
        }

        @Test
        @DisplayName("Constructor should preserve BigDecimal precision")
        void constructorShouldPreserveBigDecimalPrecision() {
            BigDecimal preciseAmount = new BigDecimal("99999.123456789");
            BigDecimal preciseRate = new BigDecimal("0.000001");

            LoanApplicationPagedResponse response = new LoanApplicationPagedResponse(
                    1L, preciseAmount, 12, "test@example.com",
                    "Personal", preciseRate, "APROBADO", BigDecimal.ZERO
            );

            assertEquals(preciseAmount, response.getMontoSolicitado());
            assertEquals(preciseRate, response.getTasaInteres());
            assertEquals(preciseAmount.scale(), response.getMontoSolicitado().scale());
            assertEquals(preciseRate.scale(), response.getTasaInteres().scale());
        }
    }

    @Nested
    @DisplayName("Chaining Operations Tests")
    class ChainingOperationsTests {

        @Test
        @DisplayName("Builder chaining should work correctly")
        void builderChaining_ShouldWorkCorrectly() {
            LoanApplicationPagedResponse response = LoanApplicationPagedResponse.builder()
                    .idSolicitud(1L)
                    .montoSolicitado(new BigDecimal("10000"))
                    .plazoMeses(12)
                    .emailUsuario("chain@example.com")
                    .tipoPrestamo("Personal")
                    .tasaInteres(new BigDecimal("15.0"))
                    .estadoSolicitud("PENDIENTE")
                    .build();

            assertEquals(1L, response.getIdSolicitud());
            assertEquals(new BigDecimal("10000"), response.getMontoSolicitado());
            assertEquals(12, response.getPlazoMeses());
            assertEquals("chain@example.com", response.getEmailUsuario());
            assertEquals("Personal", response.getTipoPrestamo());
            assertEquals(new BigDecimal("15.0"), response.getTasaInteres());
            assertEquals("PENDIENTE", response.getEstadoSolicitud());
        }

        @Test
        @DisplayName("Multiple toBuilder operations should work correctly")
        void multipleToBuilderOperations_ShouldWorkCorrectly() {
            LoanApplicationPagedResponse finalResponse = testResponse.toBuilder()
                    .montoSolicitado(new BigDecimal("20000"))
                    .build()
                    .toBuilder()
                    .plazoMeses(36)
                    .build()
                    .toBuilder()
                    .estadoSolicitud("FINAL")
                    .build();

            assertEquals(new BigDecimal("20000"), finalResponse.getMontoSolicitado());
            assertEquals(36, finalResponse.getPlazoMeses());
            assertEquals("FINAL", finalResponse.getEstadoSolicitud());

            // Original fields should be preserved
            assertEquals(testResponse.getIdSolicitud(), finalResponse.getIdSolicitud());
            assertEquals(testResponse.getEmailUsuario(), finalResponse.getEmailUsuario());
            assertEquals(testResponse.getTipoPrestamo(), finalResponse.getTipoPrestamo());
            assertEquals(testResponse.getTasaInteres(), finalResponse.getTasaInteres());
        }
    }

    @Nested
    @DisplayName("Data Integrity Tests")
    class DataIntegrityTests {

        @Test
        @DisplayName("Object should maintain data integrity after multiple operations")
        void objectShouldMaintainDataIntegrityAfterMultipleOperations() {
            // Create initial object
            LoanApplicationPagedResponse response = LoanApplicationPagedResponse.builder()
                    .idSolicitud(100L)
                    .montoSolicitado(new BigDecimal("50000.00"))
                    .build();

            // Perform multiple setter operations
            response.setPlazoMeses(24);
            response.setEmailUsuario("integrity@example.com");
            response.setTipoPrestamo("Vehicular");
            response.setTasaInteres(new BigDecimal("11.5"));
            response.setEstadoSolicitud("VERIFICADO");

            // Verify all data is correct
            assertEquals(100L, response.getIdSolicitud());
            assertEquals(new BigDecimal("50000.00"), response.getMontoSolicitado());
            assertEquals(24, response.getPlazoMeses());
            assertEquals("integrity@example.com", response.getEmailUsuario());
            assertEquals("Vehicular", response.getTipoPrestamo());
            assertEquals(new BigDecimal("11.5"), response.getTasaInteres());
            assertEquals("VERIFICADO", response.getEstadoSolicitud());
        }

        @Test
        @DisplayName("toBuilder should create independent objects")
        void toBuilderShouldCreateIndependentObjects() {
            LoanApplicationPagedResponse original = testResponse.toBuilder().build();
            LoanApplicationPagedResponse modified = testResponse.toBuilder()
                    .estadoSolicitud("MODIFICADO")
                    .build();

            // Modify the modified object further
            modified.setMontoSolicitado(new BigDecimal("99999"));

            // Original should remain unchanged
            assertEquals(testResponse.getEstadoSolicitud(), original.getEstadoSolicitud());
            assertEquals(testResponse.getMontoSolicitado(), original.getMontoSolicitado());

            // Modified should have changes
            assertEquals("MODIFICADO", modified.getEstadoSolicitud());
            assertEquals(new BigDecimal("99999"), modified.getMontoSolicitado());
        }
    }
}
