package com.example.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import org.springframework.security.core.context.SecurityContext;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        if (exchange.getRequest().getPath().value().contains("/auth")) {
            System.out.println("skipping token check cause its /auth");
            return chain.filter(exchange);
        }

        final String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        final String jwt;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("failed at header check");
            return chain.filter(exchange);
        }
        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);
        System.out.println("passed header check");

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                System.out.println("jwt is valid");
                System.out.println("authorities = " + userDetails.getAuthorities());

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetails(exchange.getRequest().getRemoteAddress().getHostName(), null));
//                ReactiveSecurityContextHolder.getContext().setAuthentication(authToken);
                ReactiveSecurityContextHolder.withAuthentication(authToken);
//                ReactiveSecurityContextHolder.getContext().block().setAuthentication(authToken);

//                System.out.println("reactive sec ctxt");
//                ReactiveSecurityContextHolder.getContext()
//                                .map((ctx) -> {
//                                    System.out.println("ctx " + ctx);
//                                    ctx.setAuthentication(authToken);
//                                    return ctx;
//                                });
////                System.out.println(rauth);
//                var monoauth = ReactiveSecurityContextHolder.getContext()
//                        .filter((c) -> c.getAuthentication() != null)
//                        .map(SecurityContext::getAuthentication);
//                var auth = monoauth.block();
//                System.out.println(auth.isAuthenticated());
//                System.out.println(auth.getDetails());
//                System.out.println(auth.getAuthorities());
//
                return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authToken));
            }
        }
        return chain.filter(exchange);
    }
}
