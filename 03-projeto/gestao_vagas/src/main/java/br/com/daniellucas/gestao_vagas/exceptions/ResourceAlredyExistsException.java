package br.com.daniellucas.gestao_vagas.exceptions;

public class ResourceAlredyExistsException extends RuntimeException {
  public ResourceAlredyExistsException() {
    super("Recurso já existe.");
  }
}
