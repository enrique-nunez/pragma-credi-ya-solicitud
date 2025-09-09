package co.com.pragma.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.annotation.AnnotationUtils;

import static org.junit.jupiter.api.Assertions.*;

public class UseCasesConfigTest {

    @Test
    void testConfigurationAnnotations() {
        assertTrue(UseCasesConfig.class.isAnnotationPresent(Configuration.class));
        assertTrue(UseCasesConfig.class.isAnnotationPresent(ComponentScan.class));
    }

    @Test
    void testComponentScanConfiguration() {
        ComponentScan componentScan = AnnotationUtils.findAnnotation(UseCasesConfig.class, ComponentScan.class);

        assertNotNull(componentScan);
        assertEquals("co.com.pragma.usecase", componentScan.basePackages()[0]);
        assertFalse(componentScan.useDefaultFilters());

        ComponentScan.Filter filter = componentScan.includeFilters()[0];
        assertEquals(FilterType.REGEX, filter.type());
        assertEquals(".*UseCase$", filter.pattern()[0]);
    }

    @Test
    void testRegexPatternMatching() {
        String pattern = "^.*UseCase$";

        assertTrue("LoanApplicationUseCase".matches(pattern));
        assertTrue("UserManagementUseCase".matches(pattern));
        assertFalse("LoanApplicationService".matches(pattern));
        assertFalse("UserRepository".matches(pattern));
    }

    @Test
    void testConfigurationInstantiation() {
        assertDoesNotThrow(() -> {
            UseCasesConfig config = new UseCasesConfig();
            assertNotNull(config);
        });
    }
}