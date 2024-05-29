package br.com.daniellucas.gestao_vagas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PrimeiroTest {
  
  @Test
  public void shouldBeAbleToSumTwoNumbers() {
    var result = sum(2, 3);

    assertEquals(5, result);
  }

  public double sum(double n1, double n2) {
    return n1 + n2;
  }
}
