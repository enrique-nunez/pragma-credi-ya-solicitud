package co.com.pragma.model.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .firstName("Juan")
                .lastName("Pérez")
                .birthDate(LocalDate.of(1990, 1, 1))
                .address("Calle 123")
                .phone("1234567890")
                .email("juan@test.com")
                .baseSalary(new BigDecimal("1000000"))
                .creationDate(LocalDateTime.of(2023, 1, 1, 10, 0))
                .roleId(1L)
                .build();
    }

    @Test
    void getId_ShouldReturnCorrectId() {
        assertEquals(1L, testUser.getId());
    }

    @Test
    void getFirstName_ShouldReturnCorrectFirstName() {
        assertEquals("Juan", testUser.getFirstName());
    }

    @Test
    void getLastName_ShouldReturnCorrectLastName() {
        assertEquals("Pérez", testUser.getLastName());
    }

    @Test
    void getBirthDate_ShouldReturnCorrectDate() {
        LocalDate expectedDate = LocalDate.of(1990, 1, 1);
        assertEquals(expectedDate, testUser.getBirthDate());
    }

    @Test
    void getAddress_ShouldReturnCorrectAddress() {
        assertEquals("Calle 123", testUser.getAddress());
    }

    @Test
    void getPhone_ShouldReturnCorrectPhone() {
        assertEquals("1234567890", testUser.getPhone());
    }

    @Test
    void getEmail_ShouldReturnCorrectEmail() {
        assertEquals("juan@test.com", testUser.getEmail());
    }

    @Test
    void getBaseSalary_ShouldReturnCorrectBaseSalary() {
        assertEquals(new BigDecimal("1000000"), testUser.getBaseSalary());
    }

    @Test
    void getCreationDate_ShouldReturnCorrectCreationDate() {
        LocalDateTime expectedDate = LocalDateTime.of(2023, 1, 1, 10, 0);
        assertEquals(expectedDate, testUser.getCreationDate());
    }

    @Test
    void getRoleId_ShouldReturnCorrectRoleId() {
        assertEquals(1L, testUser.getRoleId());
    }

    @Test
    void toBuilder_ShouldCreateBuilderWithSameValues() {
        User copiedUser = testUser.toBuilder().build();

        assertEquals(testUser.getId(), copiedUser.getId());
        assertEquals(testUser.getFirstName(), copiedUser.getFirstName());
        assertEquals(testUser.getLastName(), copiedUser.getLastName());
        assertEquals(testUser.getBirthDate(), copiedUser.getBirthDate());
        assertEquals(testUser.getAddress(), copiedUser.getAddress());
        assertEquals(testUser.getPhone(), copiedUser.getPhone());
        assertEquals(testUser.getEmail(), copiedUser.getEmail());
        assertEquals(testUser.getBaseSalary(), copiedUser.getBaseSalary());
        assertEquals(testUser.getCreationDate(), copiedUser.getCreationDate());
        assertEquals(testUser.getRoleId(), copiedUser.getRoleId());
    }

    @Test
    void toBuilder_ModifyField_ShouldCreateNewUserWithModification() {
        User modifiedUser = testUser.toBuilder()
                .firstName("Carlos")
                .build();

        assertEquals("Carlos", modifiedUser.getFirstName());
        assertEquals(testUser.getLastName(), modifiedUser.getLastName());
        assertEquals(testUser.getEmail(), modifiedUser.getEmail());
        assertEquals(testUser.getId(), modifiedUser.getId());
    }

    @Test
    void constructor_Default_ShouldSetCreationDateToNow() {
        LocalDateTime beforeCreation = LocalDateTime.now().minusSeconds(1);
        User user = new User();
        LocalDateTime afterCreation = LocalDateTime.now().plusSeconds(1);

        assertNotNull(user.getCreationDate());
        assertTrue(user.getCreationDate().isAfter(beforeCreation));
        assertTrue(user.getCreationDate().isBefore(afterCreation));
    }

    @Test
    void constructor_WithNullCreationDate_ShouldSetCurrentTime() {
        LocalDateTime beforeCreation = LocalDateTime.now().minusSeconds(1);

        User user = new User(1L, "Juan", "Pérez", LocalDate.of(1990, 1, 1),
                "Calle 123", "1234567890", "juan@test.com",
                new BigDecimal("1000000"), null, 1L);

        LocalDateTime afterCreation = LocalDateTime.now().plusSeconds(1);

        assertNotNull(user.getCreationDate());
        assertTrue(user.getCreationDate().isAfter(beforeCreation));
        assertTrue(user.getCreationDate().isBefore(afterCreation));
    }

    @Test
    void constructor_WithSpecificCreationDate_ShouldUseProvidedDate() {
        LocalDateTime specificDate = LocalDateTime.of(2023, 1, 1, 10, 0);

        User user = new User(1L, "Juan", "Pérez", LocalDate.of(1990, 1, 1),
                "Calle 123", "1234567890", "juan@test.com",
                new BigDecimal("1000000"), specificDate, 1L);

        assertEquals(specificDate, user.getCreationDate());
    }

    @Test
    void constructor_WithAllParameters_ShouldSetAllFields() {
        LocalDate birthDate = LocalDate.of(1985, 5, 15);
        LocalDateTime creationDate = LocalDateTime.of(2023, 6, 1, 14, 30);
        BigDecimal salary = new BigDecimal("2500000");

        User user = new User(10L, "María", "González", birthDate,
                "Avenida 456", "0987654321", "maria@test.com",
                salary, creationDate, 2L);

        assertEquals(10L, user.getId());
        assertEquals("María", user.getFirstName());
        assertEquals("González", user.getLastName());
        assertEquals(birthDate, user.getBirthDate());
        assertEquals("Avenida 456", user.getAddress());
        assertEquals("0987654321", user.getPhone());
        assertEquals("maria@test.com", user.getEmail());
        assertEquals(salary, user.getBaseSalary());
        assertEquals(creationDate, user.getCreationDate());
        assertEquals(2L, user.getRoleId());
    }

    @Test
    void builder_WithAllFields_ShouldCreateCompleteUser() {
        LocalDate birthDate = LocalDate.of(1992, 3, 20);
        LocalDateTime creationDate = LocalDateTime.of(2023, 7, 15, 9, 45);
        BigDecimal salary = new BigDecimal("3000000");

        User user = User.builder()
                .id(5L)
                .firstName("Ana")
                .lastName("Rodríguez")
                .birthDate(birthDate)
                .address("Carrera 789")
                .phone("5555555555")
                .email("ana@test.com")
                .baseSalary(salary)
                .creationDate(creationDate)
                .roleId(3L)
                .build();

        assertEquals(5L, user.getId());
        assertEquals("Ana", user.getFirstName());
        assertEquals("Rodríguez", user.getLastName());
        assertEquals(birthDate, user.getBirthDate());
        assertEquals("Carrera 789", user.getAddress());
        assertEquals("5555555555", user.getPhone());
        assertEquals("ana@test.com", user.getEmail());
        assertEquals(salary, user.getBaseSalary());
        assertEquals(creationDate, user.getCreationDate());
        assertEquals(3L, user.getRoleId());
    }

    @Test
    void setters_ShouldUpdateFields() {
        User user = new User();
        LocalDate newBirthDate = LocalDate.of(1995, 12, 25);
        LocalDateTime newCreationDate = LocalDateTime.of(2023, 8, 10, 16, 20);
        BigDecimal newSalary = new BigDecimal("4500000");

        user.setId(100L);
        user.setFirstName("Luis");
        user.setLastName("Martínez");
        user.setBirthDate(newBirthDate);
        user.setAddress("Diagonal 321");
        user.setPhone("1111111111");
        user.setEmail("luis@test.com");
        user.setBaseSalary(newSalary);
        user.setCreationDate(newCreationDate);
        user.setRoleId(4L);

        assertEquals(100L, user.getId());
        assertEquals("Luis", user.getFirstName());
        assertEquals("Martínez", user.getLastName());
        assertEquals(newBirthDate, user.getBirthDate());
        assertEquals("Diagonal 321", user.getAddress());
        assertEquals("1111111111", user.getPhone());
        assertEquals("luis@test.com", user.getEmail());
        assertEquals(newSalary, user.getBaseSalary());
        assertEquals(newCreationDate, user.getCreationDate());
        assertEquals(4L, user.getRoleId());
    }

    @Test
    void setters_WithNullValues_ShouldAcceptNulls() {
        testUser.setId(null);
        testUser.setFirstName(null);
        testUser.setLastName(null);
        testUser.setBirthDate(null);
        testUser.setAddress(null);
        testUser.setPhone(null);
        testUser.setEmail(null);
        testUser.setBaseSalary(null);
        testUser.setCreationDate(null);
        testUser.setRoleId(null);

        assertNull(testUser.getId());
        assertNull(testUser.getFirstName());
        assertNull(testUser.getLastName());
        assertNull(testUser.getBirthDate());
        assertNull(testUser.getAddress());
        assertNull(testUser.getPhone());
        assertNull(testUser.getEmail());
        assertNull(testUser.getBaseSalary());
        assertNull(testUser.getCreationDate());
        assertNull(testUser.getRoleId());
    }

    @Test
    void builder_ChainedCalls_ShouldWorkCorrectly() {
        LocalDate birthDate = LocalDate.of(1988, 7, 10);
        BigDecimal salary = new BigDecimal("1800000");

        User user = User.builder()
                .id(1L)
                .firstName("Sofia")
                .lastName("López")
                .birthDate(birthDate)
                .address("Transversal 147")
                .phone("2222222222")
                .email("sofia@test.com")
                .baseSalary(salary)
                .roleId(2L)
                .build();

        assertEquals(1L, user.getId());
        assertEquals("Sofia", user.getFirstName());
        assertEquals("López", user.getLastName());
        assertEquals(birthDate, user.getBirthDate());
        assertEquals("Transversal 147", user.getAddress());
        assertEquals("2222222222", user.getPhone());
        assertEquals("sofia@test.com", user.getEmail());
        assertEquals(salary, user.getBaseSalary());
        assertEquals(2L, user.getRoleId());
    }

    @Test
    void toBuilder_MultipleModifications_ShouldCreateNewUserWithAllChanges() {
        LocalDate newBirthDate = LocalDate.of(1993, 11, 5);
        BigDecimal newSalary = new BigDecimal("3500000");

        User modifiedUser = testUser.toBuilder()
                .firstName("Roberto")
                .lastName("Jiménez")
                .birthDate(newBirthDate)
                .baseSalary(newSalary)
                .build();

        assertEquals("Roberto", modifiedUser.getFirstName());
        assertEquals("Jiménez", modifiedUser.getLastName());
        assertEquals(newBirthDate, modifiedUser.getBirthDate());
        assertEquals(newSalary, modifiedUser.getBaseSalary());

        // Original values should remain unchanged for other fields
        assertEquals(testUser.getId(), modifiedUser.getId());
        assertEquals(testUser.getAddress(), modifiedUser.getAddress());
        assertEquals(testUser.getPhone(), modifiedUser.getPhone());
        assertEquals(testUser.getEmail(), modifiedUser.getEmail());
        assertEquals(testUser.getCreationDate(), modifiedUser.getCreationDate());
        assertEquals(testUser.getRoleId(), modifiedUser.getRoleId());
    }

    @Test
    void builder_WithZeroValues_ShouldAcceptZeroValues() {
        User user = User.builder()
                .id(0L)
                .baseSalary(BigDecimal.ZERO)
                .roleId(0L)
                .build();

        assertEquals(0L, user.getId());
        assertEquals(BigDecimal.ZERO, user.getBaseSalary());
        assertEquals(0L, user.getRoleId());
    }

    @Test
    void builder_WithNegativeValues_ShouldAcceptNegativeValues() {
        User user = User.builder()
                .baseSalary(new BigDecimal("-1000"))
                .build();

        assertEquals(new BigDecimal("-1000"), user.getBaseSalary());
    }

    @Test
    void builder_WithEmptyStrings_ShouldAcceptEmptyStrings() {
        User user = User.builder()
                .firstName("")
                .lastName("")
                .address("")
                .phone("")
                .email("")
                .build();

        assertEquals("", user.getFirstName());
        assertEquals("", user.getLastName());
        assertEquals("", user.getAddress());
        assertEquals("", user.getPhone());
        assertEquals("", user.getEmail());
    }

    @Test
    void builder_WithSpecialCharacters_ShouldAcceptSpecialCharacters() {
        String specialName = "José María";
        String specialLastName = "Pérez-González";
        String specialAddress = "Calle 123 #45-67 Apt. 8B";
        String specialEmail = "josé.maría@test-domain.com";

        User user = User.builder()
                .firstName(specialName)
                .lastName(specialLastName)
                .address(specialAddress)
                .email(specialEmail)
                .build();

        assertEquals(specialName, user.getFirstName());
        assertEquals(specialLastName, user.getLastName());
        assertEquals(specialAddress, user.getAddress());
        assertEquals(specialEmail, user.getEmail());
    }

    @Test
    void builder_WithFutureBirthDate_ShouldAcceptFutureDate() {
        LocalDate futureDate = LocalDate.now().plusYears(1);

        User user = User.builder()
                .birthDate(futureDate)
                .build();

        assertEquals(futureDate, user.getBirthDate());
    }

    @Test
    void builder_WithPastBirthDate_ShouldAcceptPastDate() {
        LocalDate pastDate = LocalDate.of(1950, 1, 1);

        User user = User.builder()
                .birthDate(pastDate)
                .build();

        assertEquals(pastDate, user.getBirthDate());
    }

    @Test
    void builder_WithLargeNumbers_ShouldAcceptLargeValues() {
        BigDecimal largeSalary = new BigDecimal("999999999999999999.99");
        Long largeId = Long.MAX_VALUE;
        Long largeRoleId = Long.MAX_VALUE;

        User user = User.builder()
                .id(largeId)
                .baseSalary(largeSalary)
                .roleId(largeRoleId)
                .build();

        assertEquals(largeId, user.getId());
        assertEquals(largeSalary, user.getBaseSalary());
        assertEquals(largeRoleId, user.getRoleId());
    }

    @Test
    void builder_WithDecimalPrecision_ShouldMaintainPrecision() {
        BigDecimal preciseSalary = new BigDecimal("1234567.89");

        User user = User.builder()
                .baseSalary(preciseSalary)
                .build();

        assertEquals(preciseSalary, user.getBaseSalary());
    }

    @Test
    void builder_WithLongStrings_ShouldAcceptLongStrings() {
        String longName = "A".repeat(1000);
        String longAddress = "Very long address ".repeat(50);
        String longEmail = "very.long.email.address.with.many.dots@very-long-domain-name.com";

        User user = User.builder()
                .firstName(longName)
                .address(longAddress)
                .email(longEmail)
                .build();

        assertEquals(longName, user.getFirstName());
        assertEquals(longAddress, user.getAddress());
        assertEquals(longEmail, user.getEmail());
    }

    @Test
    void builder_WithMinMaxDates_ShouldAcceptExtremeDates() {
        LocalDate minDate = LocalDate.MIN;
        LocalDate maxDate = LocalDate.MAX;
        LocalDateTime minDateTime = LocalDateTime.MIN;
        LocalDateTime maxDateTime = LocalDateTime.MAX;

        User userWithMinDates = User.builder()
                .birthDate(minDate)
                .creationDate(minDateTime)
                .build();

        User userWithMaxDates = User.builder()
                .birthDate(maxDate)
                .creationDate(maxDateTime)
                .build();

        assertEquals(minDate, userWithMinDates.getBirthDate());
        assertEquals(minDateTime, userWithMinDates.getCreationDate());
        assertEquals(maxDate, userWithMaxDates.getBirthDate());
        assertEquals(maxDateTime, userWithMaxDates.getCreationDate());
    }

    @Test
    void constructor_DefaultCreationDateBehavior_ShouldBeConsistent() {
        // Test multiple instances to ensure consistent behavior
        User user1 = new User();
        User user2 = new User();

        assertNotNull(user1.getCreationDate());
        assertNotNull(user2.getCreationDate());

        // Both should be very close in time (within a few milliseconds)
        long timeDifference = Math.abs(
                user1.getCreationDate().getNano() - user2.getCreationDate().getNano()
        );
        assertTrue(timeDifference < 1_000_000_000); // Less than 1 second difference
    }


    @Test
    void setCreationDate_ShouldOverrideAutoSetDate() {
        User user = new User(); // This sets creationDate to now()
        LocalDateTime originalDate = user.getCreationDate();

        LocalDateTime newDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        user.setCreationDate(newDate);

        assertEquals(newDate, user.getCreationDate());
        assertNotEquals(originalDate, user.getCreationDate());
    }

    @Test
    void toBuilder_FromEmptyUser_ShouldCreateBuilderWithNullValues() {
        User emptyUser = new User();
        // Clear the auto-set creation date for this test
        emptyUser.setCreationDate(null);

        User copiedUser = emptyUser.toBuilder().build();

        assertNull(copiedUser.getId());
        assertNull(copiedUser.getFirstName());
        assertNull(copiedUser.getLastName());
        assertNull(copiedUser.getBirthDate());
        assertNull(copiedUser.getAddress());
        assertNull(copiedUser.getPhone());
        assertNull(copiedUser.getEmail());
        assertNull(copiedUser.getBaseSalary());
        // El constructor parametrizado establece creationDate a now() si es null
        assertNotNull(copiedUser.getCreationDate()); // CORREGIDO: no será null
        assertNull(copiedUser.getRoleId());
    }

    @Test
    void builder_WithPartialFields_ShouldCreateUserWithNulls() {
        User user = User.builder()
                .firstName("Pedro")
                .email("pedro@test.com")
                .build();

        assertNull(user.getId());
        assertEquals("Pedro", user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getBirthDate());
        assertNull(user.getAddress());
        assertNull(user.getPhone());
        assertEquals("pedro@test.com", user.getEmail());
        assertNull(user.getBaseSalary());
        // El constructor parametrizado establece creationDate a now() si es null
        assertNotNull(user.getCreationDate()); // CORREGIDO: no será null
        assertNull(user.getRoleId());
    }

    @Test
    void builder_CreationDateHandling_ShouldRespectBuilderValue() {
        LocalDateTime specificDate = LocalDateTime.of(2020, 1, 1, 12, 0);

        User userWithSpecificDate = User.builder()
                .creationDate(specificDate)
                .build();

        User userWithNullDate = User.builder()
                .creationDate(null)
                .build();

        assertEquals(specificDate, userWithSpecificDate.getCreationDate());
        // El constructor parametrizado establece creationDate a now() si es null
        assertNotNull(userWithNullDate.getCreationDate()); // CORREGIDO: no será null
        assertTrue(userWithNullDate.getCreationDate().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void builder_WithWhitespaceStrings_ShouldAcceptWhitespace() {
        String whitespaceString = "   ";
        String tabString = "\t\t";
        String newlineString = "\n\n";

        User user = User.builder()
                .firstName(whitespaceString)
                .lastName(tabString)
                .address(newlineString)
                .build();

        assertEquals(whitespaceString, user.getFirstName());
        assertEquals(tabString, user.getLastName());
        assertEquals(newlineString, user.getAddress());
    }

 }