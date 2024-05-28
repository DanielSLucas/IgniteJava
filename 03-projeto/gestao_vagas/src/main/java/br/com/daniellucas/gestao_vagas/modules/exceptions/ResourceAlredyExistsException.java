package br.com.daniellucas.gestao_vagas.modules.exceptions;

public class ResourceAlredyExistsException extends RuntimeException {
  public ResourceAlredyExistsException() {
    super("Recurso já existe.");
  }
}
