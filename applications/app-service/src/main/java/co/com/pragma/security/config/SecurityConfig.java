package co.com.pragma.security.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    private static final String[] PUBLIC_PATHS = {
            "/actuator/health",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/**",  // Añadir esto
            "/configuration/**",      // Añadir esto
            "/favicon.ico"           // Añadir esto
    };

    @Bean
    SecurityWebFilterChain springSecurity(ServerHttpSecurity http,
                                          Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthConverter) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .authorizeExchange(ex -> ex
                        .pathMatchers(PUBLIC_PATHS).permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/v1/solicitud/search").hasAnyAuthority("ASESOR")
                        .pathMatchers(HttpMethod.POST, "/api/v1/solicitud/**").hasAuthority("CLIENT")
                        .pathMatchers("/api/**").authenticated() // Solo proteger rutas de API
                        .anyExchange().permitAll() // Permitir todo lo demás (incluyendo Swagger)
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((exchange, ex) -> {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        })
                )
                .build();
    }

    @Bean
    ReactiveJwtDecoder jwtDecoder(
            @Value("${security.jwt.secret}") String secret,
            @Value("${app.issuer}") String issuer,
            @Value("${app.audience}") String audience) {

        byte[] bytes = secret.matches("^[A-Za-z0-9+/=]+$")
                ? Decoders.BASE64.decode(secret)
                : secret.getBytes(StandardCharsets.UTF_8);
        SecretKey key = Keys.hmacShaKeyFor(bytes);

        var decoder = NimbusReactiveJwtDecoder.withSecretKey(key)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();

        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);

        OAuth2TokenValidator<Jwt> withAudience = jwt -> {
            var aud = jwt.getAudience();
            return (aud != null && aud.contains(audience))
                    ? OAuth2TokenValidatorResult.success()
                    : OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token", "audience inválida", ""));
        };

        JwtTimestampValidator withSkew = new JwtTimestampValidator(Duration.ofSeconds(120));

        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(withIssuer, withAudience, withSkew));
        return decoder;
    }

    @Bean
    Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthConverter() {
        return (Jwt jwt) -> {
            var authorities = extractAuthorities(jwt);
            return Mono.just(new JwtAuthenticationToken(jwt, authorities, jwt.getSubject()));
        };
    }

    private Collection<SimpleGrantedAuthority> extractAuthorities(Jwt jwt) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        // Intentar obtener roles de diferentes claims
        // Primero intentar "roles" (plural)
        List<String> roles = jwt.getClaimAsStringList("roles");
        if (roles != null && !roles.isEmpty()) {
            authorities.addAll(roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList()));
        } else {
            // Si no existe "roles", intentar "rol" (singular)
            String singleRole = jwt.getClaimAsString("rol");
            if (singleRole != null && !singleRole.isEmpty()) {
                authorities.add(new SimpleGrantedAuthority(singleRole));
            }
        }

        return authorities;
    }
}
