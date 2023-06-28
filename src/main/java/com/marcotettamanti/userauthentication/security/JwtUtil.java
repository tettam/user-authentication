package com.marcotettamanti.userauthentication.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.marcotettamanti.userauthentication.model.entities.User;



@Component
public class JwtUtil {
  private static int MILLISECONDS_VALIDATION_TOKEN = 900;

  Date currentTime = new Date();
  Date expirationDateToken = new Date(currentTime.getTime() + MILLISECONDS_VALIDATION_TOKEN);

  public String generateTokenUsername(User user){
    return JWT.create()
              .withIssuer("ValidacaoUsuario")
              .withSubject(user.getUsername())
              .withClaim("id", user.getId())
              .withIssuedAt(currentTime)
              .withExpiresAt(expirationDateToken)
              .sign(Algorithm.HMAC256("secretKey"));
  }

  public String validateToken(String token){
    return JWT.require(Algorithm.HMAC256("secretKey"))
        .withIssuer("ValidacaoUsuario")
        .build().verify(token).getSubject();
  }
}
