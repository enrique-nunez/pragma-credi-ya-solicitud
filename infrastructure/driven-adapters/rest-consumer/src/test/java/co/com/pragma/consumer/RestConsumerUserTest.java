package co.com.pragma.consumer;

import co.com.pragma.model.common.models.BaseResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Function;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestConsumerUserTest {

    @Mock
    private WebClient webClient;

    @InjectMocks
    private RestConsumerUser restConsumerUser;

    @Test
    @SuppressWarnings("unchecked")
    void existUserByEmail_shouldReturnTrue_whenUserExists() {
        // Given
        String email = "test@correo.com";
        String token = "jwt-token";
        BaseResponse<Boolean> baseResponse = new BaseResponse<>();
        baseResponse.setData(true);

        // Setup JWT Authentication
        Jwt jwt = createMockJwt(token);
        JwtAuthenticationToken jwtAuth = new JwtAuthenticationToken(jwt);
        SecurityContext securityContext = createMockSecurityContext(jwtAuth);

        // Mock WebClient chain
        setupWebClientMocks(baseResponse);

        // When & Then
        try (MockedStatic<ReactiveSecurityContextHolder> mockedContext = mockStatic(ReactiveSecurityContextHolder.class)) {
            mockedContext.when(ReactiveSecurityContextHolder::getContext)
                    .thenReturn(Mono.just(securityContext));

            Mono<Boolean> result = restConsumerUser.existUserByEmail(email);

            StepVerifier.create(result)
                    .expectNext(true)
                    .verifyComplete();
        }

        // Verify interactions
        verify(webClient).get();
    }

    @Test
    @SuppressWarnings("unchecked")
    void existUserByEmail_shouldReturnFalse_whenUserDoesNotExist() {
        // Given
        String email = "nonexistent@correo.com";
        String token = "jwt-token";
        BaseResponse<Boolean> baseResponse = new BaseResponse<>();
        baseResponse.setData(false);

        // Setup JWT Authentication
        Jwt jwt = createMockJwt(token);
        JwtAuthenticationToken jwtAuth = new JwtAuthenticationToken(jwt);
        SecurityContext securityContext = createMockSecurityContext(jwtAuth);

        // Mock WebClient chain
        setupWebClientMocks(baseResponse);

        // When & Then
        try (MockedStatic<ReactiveSecurityContextHolder> mockedContext = mockStatic(ReactiveSecurityContextHolder.class)) {
            mockedContext.when(ReactiveSecurityContextHolder::getContext)
                    .thenReturn(Mono.just(securityContext));

            Mono<Boolean> result = restConsumerUser.existUserByEmail(email);

            StepVerifier.create(result)
                    .expectNext(false)
                    .verifyComplete();
        }
    }

    @Test
    void existUserByEmail_shouldThrowException_whenNoTokenInContext() {
        // Given
        String email = "test@correo.com";
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(null);

        // When & Then
        try (MockedStatic<ReactiveSecurityContextHolder> mockedContext = mockStatic(ReactiveSecurityContextHolder.class)) {
            mockedContext.when(ReactiveSecurityContextHolder::getContext)
                    .thenReturn(Mono.just(securityContext));

            Mono<Boolean> result = restConsumerUser.existUserByEmail(email);

            StepVerifier.create(result)
                    .expectError(RuntimeException.class)
                    .verify();
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    void existUserByEmail_shouldHandleWebClientError() {
        // Given
        String email = "test@correo.com";
        String token = "jwt-token";

        // Setup JWT Authentication
        Jwt jwt = createMockJwt(token);
        JwtAuthenticationToken jwtAuth = new JwtAuthenticationToken(jwt);
        SecurityContext securityContext = createMockSecurityContext(jwtAuth);

        // Mock WebClient chain to throw exception
        setupWebClientMocksWithError();

        // When & Then
        try (MockedStatic<ReactiveSecurityContextHolder> mockedContext = mockStatic(ReactiveSecurityContextHolder.class)) {
            mockedContext.when(ReactiveSecurityContextHolder::getContext)
                    .thenReturn(Mono.just(securityContext));

            Mono<Boolean> result = restConsumerUser.existUserByEmail(email);

            StepVerifier.create(result)
                    .expectError(RuntimeException.class)
                    .verify();
        }
    }

    @Test
    void fallbackExistUserByEmail_shouldReturnFalse() {
        // When
        Mono<Boolean> result = restConsumerUser.fallbackExistUserByEmail("test@correo.com", new Exception("Service unavailable"));

        // Then
        StepVerifier.create(result)
                .expectNext(false)
                .verifyComplete();
    }

    // Helper methods to reduce code duplication
    private Jwt createMockJwt(String token) {
        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn(token);
        return jwt;
    }

    private SecurityContext createMockSecurityContext(JwtAuthenticationToken jwtAuth) {
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(jwtAuth);
        return securityContext;
    }

    @SuppressWarnings("unchecked")
    private void setupWebClientMocks(BaseResponse<Boolean> baseResponse) {
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header(anyString(), anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
                .thenReturn(Mono.just(baseResponse));
    }

    @SuppressWarnings("unchecked")
    private void setupWebClientMocksWithError() {
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header(anyString(), anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
                .thenReturn(Mono.error(new RuntimeException("Service unavailable")));
    }
}