package br.com.daniellucas.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.daniellucas.gestao_vagas.exceptions.ResourceNotFoundException;
import br.com.daniellucas.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.daniellucas.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.daniellucas.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.daniellucas.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {
  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private ApplyJobRepository applyJobRepository;

  public ApplyJobEntity execute(UUID candidateId, UUID jobId) {

    this.candidateRepository.findById(candidateId)
      .orElseThrow(() -> {
        throw new ResourceNotFoundException();
      });

    this.jobRepository.findById(jobId)
      .orElseThrow(() -> {
        throw new ResourceNotFoundException();
      });

    var jobApplication = ApplyJobEntity.builder()
      .candidateId(candidateId)
      .jobId(jobId)
      .build();

    return applyJobRepository.save(jobApplication);
  }
}
