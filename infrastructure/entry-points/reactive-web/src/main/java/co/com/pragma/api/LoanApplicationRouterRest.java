package co.com.pragma.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Tag(name = "LoanApplicationHandler", description = "Operaciones para la administración de solicitudes de préstamo")
public class LoanApplicationRouterRest {

    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/solicitud",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "saveLoanApplication",
                            summary = "Crear una nueva solicitud de préstamo",
                            tags = {"LoanApplicationHandler"},
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Datos para crear la solicitud",
                                    required = true,
                                    content = @io.swagger.v3.oas.annotations.media.Content(
                                            mediaType = "application/json",
                                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                                    name = "SolicitudPréstamo",
                                                    value = "{ \"email\": \"mariel.mirely.mendoza@gmail.com\", \"amount\": 76450, \"loanTypeId\": 4, \"term\": 8 }"
                                            )
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Solicitud creada exitosamente",
                                            content = @io.swagger.v3.oas.annotations.media.Content(
                                                    mediaType = "application/json",
                                                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                                            name = "RespuestaCreación",
                                                            value = "{ \"success\": true, \"message\": \"Operación exitosa\", \"data\": { \"amount\": 76450, \"loanTypeId\": 4, \"term\": 8, \"email\": \"mariel.mirely.mendoza@gmail.com\" }, \"stateCode\": 201 }"
                                                    )
                                            )
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/solicitud/search",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "getLoanApplicationsSearch",
                            summary = "Buscar solicitudes de préstamo",
                            tags = {"LoanApplicationHandler"},
                            parameters = {
                                    @Parameter(name = "page", in = ParameterIn.QUERY, description = "Página"),
                                    @Parameter(name = "size", in = ParameterIn.QUERY, description = "Tamaño de página")
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Lista paginada de solicitudes",
                                            content = @io.swagger.v3.oas.annotations.media.Content(
                                                    mediaType = "application/json",
                                                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                                            name = "RespuestaBusqueda",
                                                            value = "{ \"success\": true, \"message\": \"Operación exitosa\", \"data\": [ { \"idSolicitud\": 1, \"montoSolicitado\": 12900.00, \"plazoMeses\": 24, \"emailUsuario\": \"ludith.mendoza.20@gmail.com\", \"tipoPrestamo\": \"Personal\", \"tasaInteres\": 12.50, \"estadoSolicitud\": \"Pendiente de revisión\", \"nombreUsuario\": \"Ludith Mendoza Bu.\", \"deudaTotalMensual\": 0 } ], \"pagination\": { \"page\": 0, \"size\": 1, \"totalElements\": 7, \"totalPages\": 7 } }"
                                                    )
                                            )
                                    )
                            }
                    )
            )
    })
    @Bean
    public RouterFunction<ServerResponse> loanApplicationRouterFunction(LoanApplicationHandler loanApplicationHandler) {
        return route(POST("/api/v1/solicitud"), loanApplicationHandler::saveLoanApplication)
                .andRoute(GET("/api/v1/solicitud/search"), loanApplicationHandler::getLoanApplicationsSearch)
                .andRoute(PUT("/api/v1/solicitud/{id}"), loanApplicationHandler::updateStatusLoanApplication);
    }
}
