package co.com.pragma.model.user.gateways;

import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<Boolean> existUserByEmail(String email);

}
