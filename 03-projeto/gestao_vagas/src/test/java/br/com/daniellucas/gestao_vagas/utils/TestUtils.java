package br.com.daniellucas.gestao_vagas.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
  public static String objectToJSON(Object obj) {
    try {
      final ObjectMapper objectMapper = new ObjectMapper();

      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String generateToken(UUID id) {
    return JWT.create()
      .withIssuer("javagas")
      .withExpiresAt(Instant.now().plus(Duration.ofMinutes(5)))
      .withSubject(id.toString())
      .withClaim("roles", Arrays.asList("COMPANY"))
      .sign(Algorithm.HMAC256("JAVAGAS_@12341"));
  }
}
