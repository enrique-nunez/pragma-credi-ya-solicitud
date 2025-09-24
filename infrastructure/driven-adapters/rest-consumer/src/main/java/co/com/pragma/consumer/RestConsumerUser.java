package co.com.pragma.consumer;

import co.com.pragma.model.common.models.BaseResponse;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestConsumerUser implements UserRepository {

    private final WebClient client;

    @CircuitBreaker(name = "existUserByEmail", fallbackMethod = "fallbackExistUserByEmail")
    public Mono<Boolean> existUserByEmail(String email) {
        log.info("Verificando existencia de usuario con email: {}", email);
        return getCurrentToken()
                .flatMap(jwtToken -> client
                        .get()
                        .uri(uriBuilder -> uriBuilder.path("/api/v1/usuarios/validar/{email}").build(email))
                        .header("Authorization", "Bearer " + jwtToken)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<BaseResponse<Boolean>>() {})
                        .map(BaseResponse::getData)
                        .doOnSuccess(result -> log.info("Resultado de existencia para {}: {}", email, result))
                        .doOnError(error -> log.error("Error al verificar usuario: {}", error.getMessage(), error))
                );
    }

    public Mono<Boolean> fallbackExistUserByEmail(String email, Exception exception) {
        log.warn("Fallback activado para verificar usuario {}: {}", email, exception.getMessage());
        return Mono.just(false);
    }

    // obtener todos los usuarios
    @CircuitBreaker(name = "getAllUsers", fallbackMethod = "fallbackGetAllUsers")
    public Mono<List<User>> getAllUsers() {
        log.info("Obteniendo todos los usuarios");
        return getCurrentToken()
                .flatMap(jwtToken -> client
                        .get()
                        .uri("/api/v1/usuarios")
                        .header("Authorization", "Bearer " + jwtToken)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<User>>>() {})
                        .map(BaseResponse::getData)
                        .doOnSuccess(users -> log.info("Usuarios obtenidos: {}", users.size()))
                        .doOnError(error -> log.error("Error al obtener usuarios: {}", error.getMessage(), error))
                );
    }

    public Mono<List<User>> fallbackGetAllUsers(Exception exception) {
        log.warn("Fallback activado para obtener usuarios: {}", exception.getMessage());
        return Mono.just(Collections.emptyList());
    }

    /**
     * Obtiene el token JWT del contexto de seguridad actual
     */
    private Mono<String> getCurrentToken() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(auth -> auth instanceof JwtAuthenticationToken)
                .cast(JwtAuthenticationToken.class)
                .map(jwtAuth -> jwtAuth.getToken().getTokenValue())
                .doOnNext(token -> log.debug("Token extraído del contexto de seguridad"))
                .switchIfEmpty(Mono.error(new RuntimeException("No se encontró token JWT en el contexto de seguridad")));
    }

    /**
     * Método utilitario para extraer datos del token (si es necesario en el futuro)
     */
    private Mono<Void> extraerDatosDelToken() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(a -> a instanceof JwtAuthenticationToken)
                .map(a -> (JwtAuthenticationToken) a)
                .doOnNext(jwtAuth -> {
                    String email = jwtAuth.getToken().getClaim("email");
                    String id = jwtAuth.getToken().getClaim("id");
                    Object roles = jwtAuth.getToken().getClaim("roles");

                    log.info("Email: {}", email);
                    log.info("ID: {}", id);
                    log.info("Roles: {}", roles);
                })
                .then();
    }
}
