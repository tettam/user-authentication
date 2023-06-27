package com.marcotettamanti.userauthentication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

  @Bean
  public AuthFilterToken authFilterToken(){
    return new AuthFilterToken();
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authentication) throws Exception{
    return authentication.getAuthenticationManager();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(auth -> {
        auth.requestMatchers("/api/users/management/**").permitAll();
        auth.anyRequest().authenticated();}) 
      .httpBasic(basic -> basic.disable())
      .csrf(cross -> cross.disable())
      .cors(cors -> cors.disable())
      .addFilterBefore(authFilterToken(), UsernamePasswordAuthenticationFilter.class)
      .build();
  }


  @Bean
  @Order(1)
  public SecurityFilterChain h2ConsoleSecurityFilter(HttpSecurity http) throws Exception{
    return http
      .securityMatcher(AntPathRequestMatcher.antMatcher("/h2-console/**"))
      .httpBasic(Customizer.withDefaults())
      .httpBasic(Customizer.withDefaults())
      .csrf(cross -> cross.disable())
      .headers(header -> header.frameOptions(op -> op.disable()))
      .authorizeHttpRequests(request -> {
        request.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll();
        request.anyRequest().authenticated();})
      .build();
  }
}