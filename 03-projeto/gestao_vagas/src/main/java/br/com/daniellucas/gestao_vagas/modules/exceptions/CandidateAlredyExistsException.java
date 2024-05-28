package br.com.daniellucas.gestao_vagas.modules.exceptions;

public class CandidateAlredyExistsException extends RuntimeException {
  public CandidateAlredyExistsException() {
    super("Candidato já existe.");
  }
}
