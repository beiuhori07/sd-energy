//package com.sd.device.config;
//
//import io.jsonwebtoken.Claims;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.lang.NonNull;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.core.GrantedAuthorityDefaults;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.function.Function;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//  private final JwtService jwtService;
//  private final UserDetailsService userDetailsService;
//
//  @Override
//  protected void doFilterInternal(
//      @NonNull HttpServletRequest request,
//      @NonNull HttpServletResponse response,
//      @NonNull FilterChain filterChain)
//      throws ServletException, IOException {
//
//    if (request.getServletPath().contains("/auth")) {
//      filterChain.doFilter(request, response);
//      return;
//    }
//    final String authHeader = request.getHeader("Authorization");
//    final String jwt;
//    final String username;
//    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//      filterChain.doFilter(request, response);
//      System.out.println("failed at header check");
//      return;
//    }
//    jwt = authHeader.substring(7);
//    username = jwtService.extractUsername(jwt);
//    System.out.println("passed header check");
//
//    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//      if (jwtService.isTokenValid(jwt, userDetails)) {
//        String role = jwtService.extractRole(jwt);
//        System.out.println("jwt is valid");
//        System.out.println("role = " + role);
//        System.out.println("authorities = " + userDetails.getAuthorities());
//        System.out.println("authorities = " + role.substring(5));
//
//        UsernamePasswordAuthenticationToken authToken =
//            new UsernamePasswordAuthenticationToken(
//                userDetails, null, Arrays.asList(new SimpleGrantedAuthority(role)));
//
//        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//      }
//    }
//    filterChain.doFilter(request, response);
//  }
//
//}
