package com.marcotettamanti.userauthentication.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class WebSecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authentication) throws Exception{
    return authentication.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
    .cors(cors -> {
      cors.configurationSource(request -> {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("*");
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");
        return corsConfig;
      });
    })
    .csrf(cross -> {
      cross.disable();
    })
    .sessionManagement(session -> {
      session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    })
    .authorizeHttpRequests(request -> {
      request.requestMatchers("api/users/management/**").permitAll();
      request.anyRequest().authenticated();
    });
      
    return http.build();
  }
}
