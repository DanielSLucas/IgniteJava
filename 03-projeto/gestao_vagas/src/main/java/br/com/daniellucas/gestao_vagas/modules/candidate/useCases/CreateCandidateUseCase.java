package br.com.daniellucas.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.daniellucas.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.daniellucas.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.daniellucas.gestao_vagas.modules.exceptions.ResourceAlredyExistsException;

@Service
public class CreateCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  public CandidateEntity execute(CandidateEntity candidateEntity) {
    this.candidateRepository
      .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
      .ifPresent((user) -> {
        throw new ResourceAlredyExistsException();
      });

    return this.candidateRepository.save(candidateEntity);
  }
}
