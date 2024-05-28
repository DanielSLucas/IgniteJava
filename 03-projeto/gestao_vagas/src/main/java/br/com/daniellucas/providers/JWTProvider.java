package br.com.daniellucas.providers;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class JWTProvider {
  
  @Value("${security.token.secret}")
  private String secretKey;

  public String generateToken(String sub) {
    return JWT.create()
      .withIssuer("javagas")
      .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
      .withSubject(sub)
      .sign(this.getAlgorithm());
  }
  
  private Algorithm getAlgorithm() {
    return Algorithm.HMAC256(secretKey);
  }

  public String validateToken(String token) {
    token = token.replace("Bearer ", "");

    try {
      var sub = JWT.require(this.getAlgorithm())
        .build()
        .verify(token)
        .getSubject();
      
      return sub;
    } catch (JWTVerificationException e) {
      e.printStackTrace();
      return "";
    }

  }
}
