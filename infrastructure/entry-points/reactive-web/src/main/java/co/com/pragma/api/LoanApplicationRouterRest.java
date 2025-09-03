package co.com.pragma.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Tag(name = "UserHandler", description = "Operaciones para la administración de usuarios")
public class LoanApplicationRouterRest {

    @Bean
    public RouterFunction<ServerResponse> loanApplicationRouterFunction(LoanApplicationHandler loanApplicationHandler) {
        return route(POST("/api/v1/solicitud"), loanApplicationHandler::saveLoanApplication);
    }
}
