package br.com.daniellucas.gestao_vagas.exceptions;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException() {
    super("Recurso não encontrado");
  }
}
