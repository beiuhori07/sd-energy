package com.sd.monitoring.config;



//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//@EnableMethodSecurity
//public class SecurityConfig {
//
//      private final JwtAuthenticationFilter jwtAuthFilter;
//      private final AuthenticationProvider authenticationProvider;

//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http
////            .cors()
////        .and()
//        .csrf(AbstractHttpConfigurer::disable)
//                        .authorizeHttpRequests(req ->
////                                        req.requestMatchers(GET, "/monitoring/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
//                                               req.anyRequest()
//                                                .permitAll()
//                        )
//                        .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
//                        .authenticationProvider(authenticationProvider)
//                        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//    return http.build();
//  }

//  @Bean
//  public CorsConfigurationSource corsConfigurationSource() {
//    CorsConfiguration configuration = new CorsConfiguration();
//    configuration.setAllowedOrigins(
//        Arrays.asList(
//            "http://localhost",
//            "http://localhost:80",
//            "http://localhost:81",
//            "http://react:80",
//            "http://react:81",
//            "http://react",
//            "http://localhost:3000",
//            "http://127.0.0.1:3000"));
//    configuration.setAllowedMethods(
//        Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//    configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "origin"));
//    configuration.setAllowCredentials(true);
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", configuration);
//    return source;
//  }
//}
