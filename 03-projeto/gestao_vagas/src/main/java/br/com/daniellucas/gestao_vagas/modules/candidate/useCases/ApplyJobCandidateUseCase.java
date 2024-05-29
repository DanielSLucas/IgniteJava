package br.com.daniellucas.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.daniellucas.gestao_vagas.exceptions.ResourceNotFoundException;
import br.com.daniellucas.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.daniellucas.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {
  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private JobRepository JobRepository;

  public void execute(UUID candidateId, UUID jobId) {

    this.candidateRepository.findById(candidateId)
      .orElseThrow(() -> {
        throw new ResourceNotFoundException();
      });

    this.JobRepository.findById(jobId)
      .orElseThrow(() -> {
        throw new ResourceNotFoundException();
      });
  }
}
