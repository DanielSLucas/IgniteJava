package br.com.daniellucas.gestao_vagas.providers;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTProvider {
  
  @Value("${security.token.secret}")
  private String secretKey;

  public String generateToken(String sub, Instant expiresAt, List<String> roles) {
    return JWT.create()
      .withIssuer("javagas")
      .withExpiresAt(expiresAt)
      .withSubject(sub)
      .withClaim("roles", roles)
      .sign(this.getAlgorithm());
  }
  
  private Algorithm getAlgorithm() {
    return Algorithm.HMAC256(secretKey);
  }

  public DecodedJWT validateToken(String token) {
    token = token.replace("Bearer ", "");

    try {
      var sub = JWT.require(this.getAlgorithm())
        .build()
        .verify(token);
      
      return sub;
    } catch (JWTVerificationException e) {
      e.printStackTrace();
      return null;
    }

  }
}
