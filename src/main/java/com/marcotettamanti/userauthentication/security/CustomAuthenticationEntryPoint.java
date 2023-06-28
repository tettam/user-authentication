package com.marcotettamanti.userauthentication.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      org.springframework.security.core.AuthenticationException authException) 
          throws IOException, ServletException {
    
    int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
    String message = "Acesso não autorizado";
    String requestPath = request.getRequestURI();

    if(authException instanceof BadCredentialsException) {
      message = "Login ou senha inválidos";
    } else if (authException instanceof LockedException) {
      statusCode = HttpServletResponse.SC_FORBIDDEN;
      message = "Login ou senha inválidos";
    }

    Map<String, Object> messageBody = new HashMap<>();
    messageBody.put("status", statusCode);
    messageBody.put("error" , "Unauthorized");
    messageBody.put("path", requestPath);
    messageBody.put("message" , message);

    ObjectMapper objetoMapper = new ObjectMapper();
    String jsonResponse = objetoMapper.writeValueAsString(messageBody);
    response.setContentType("applicaiton/json");
    response.setStatus(statusCode);
    response.getWriter().write(jsonResponse);
  }
}