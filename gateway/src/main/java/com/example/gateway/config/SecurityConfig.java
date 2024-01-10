package com.example.gateway.config;

import com.example.gateway.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final ReactiveUserDetailsService userDetailsService;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        http

                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .csrf()
                .disable()

                .cors()
                .and()


                .authorizeExchange()
                    .pathMatchers("/api/auth/**").permitAll()
                    .pathMatchers(OPTIONS, "/api/**").permitAll()
                    .pathMatchers(GET, "/api/user/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                    .pathMatchers("/api/user/**").hasAnyRole(Role.ADMIN.name())
                    .pathMatchers("/api/monitoring/ws-test/info").permitAll()
                    .pathMatchers("/api/monitoring/ws-test/**").permitAll()
                    .pathMatchers(GET, "/api/monitoring/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                    .pathMatchers(GET, "/api/device/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                    .pathMatchers("/api/device/**").hasAnyRole(Role.ADMIN.name())
                    .pathMatchers("/api/chat/ws-test/info").permitAll()
                    .pathMatchers("/api/chat/ws-test/**").permitAll()
                    .pathMatchers("/api/chat/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                    .anyExchange().authenticated()

                .and()
                .authenticationManager(new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService) {
                })
                .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        ;

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH",
                "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type"));
        configuration.setAllowCredentials(true);
//        configuration.cred
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
