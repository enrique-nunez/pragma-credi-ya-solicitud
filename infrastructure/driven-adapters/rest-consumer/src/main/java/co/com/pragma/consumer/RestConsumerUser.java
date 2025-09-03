package co.com.pragma.consumer;

import co.com.pragma.model.common.models.BaseResponse;
import co.com.pragma.model.user.gateways.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestConsumerUser implements UserRepository {

    private final WebClient client;

    @CircuitBreaker(name = "existUserByEmail", fallbackMethod = "testExistUserByEmailOk")
    public Mono<Boolean> existUserByEmail(String email) {
        log.info("Verificando existencia de usuario con email: {}", email);
        return client
                .get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/usuarios/email/validate/{email}").build(email))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<BaseResponse<Boolean>>() {})
                .map(BaseResponse::getData)
                .doOnSuccess(result -> log.info("Resultado de existencia para {}: {}", email, result))
                .doOnError(error -> log.error("Error al verificar usuario: {}", error.getMessage(), error));
    }

    public Mono<String> testExistUserByEmailOk(Exception ignored) {
        log.warn("Entrando al método de fallback testExistUserByEmailOk");
        return client
                .get()
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> log.error("Error en fallback: {}", error.getMessage(), error));
    }

}
