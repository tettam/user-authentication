package com.marcotettamanti.userauthentication.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.marcotettamanti.userauthentication.model.entities.User;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;



@Component
public class JwtUtil {
  private static int MILLISECONDS_VALIDATION_TOKEN = 900000;
  private static final String keySecrety = "keysecretyToken";
  private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

  Date currentTime = new Date();
  Date expirationDateToken = new Date(currentTime.getTime() + MILLISECONDS_VALIDATION_TOKEN);

  public String generateTokenUsername(User user){
    return Jwts
      .builder()
      .setSubject(user.getEmail())
      .setIssuedAt(currentTime)
      .claim("id", user.getId())
      .setExpiration(expirationDateToken)
      .signWith(SignatureAlgorithm.HS512, keySecrety)
      .compact();
  }

  public boolean validationToke(String token){
    try {
      
      Jwts.parser().setSigningKey(keySecrety).parseClaimsJws(token);
      return true;
    } catch (SignatureException e) {
      logger.error("Assinatura inválida", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("Token expirou!", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("Token não suportado", e.getMessage());
    }
    return false;
  }

  public String getEmailToken(String token){
    return Jwts.parser().setSigningKey(keySecrety).parseClaimsJws(token).getBody().getSubject();
  }
}
