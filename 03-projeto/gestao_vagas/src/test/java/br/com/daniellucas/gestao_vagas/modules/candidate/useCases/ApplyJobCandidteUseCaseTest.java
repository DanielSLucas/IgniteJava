package br.com.daniellucas.gestao_vagas.modules.candidate.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.daniellucas.gestao_vagas.exceptions.ResourceNotFoundException;
import br.com.daniellucas.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.daniellucas.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.daniellucas.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.daniellucas.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.daniellucas.gestao_vagas.modules.company.entities.JobEntity;
import br.com.daniellucas.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidteUseCaseTest {

  @InjectMocks
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private JobRepository JobRepository;

  @Mock
  private ApplyJobRepository applyJobRepository;

  @Test
  @DisplayName("Should not be able to apply for a job with no candidate")
  public void should_not_be_able_to_apply_job_with_no_candidate() {
    try {
      this.applyJobCandidateUseCase.execute(null, null);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(ResourceNotFoundException.class);
    }
  }

  @Test
  @DisplayName("Should not be able to apply for a non-existing job")
  public void should_not_be_able_to_apply_for_a_non_existing_job() {
    var candidateId = UUID.randomUUID();

    when(candidateRepository.findById(candidateId))
      .thenReturn(Optional.of(CandidateEntity.builder().id(candidateId).build()));

    try {
      this.applyJobCandidateUseCase.execute(candidateId, null);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(ResourceNotFoundException.class);
    }
  }

  @Test
  public void should_be_able_to_create_a_new_apply_job() {
    var candidateId = UUID.randomUUID();
    var jobId = UUID.randomUUID();
    var jobApplication = ApplyJobEntity.builder()
      .candidateId(candidateId)
      .jobId(jobId)
      .build();

    var createdJobApplication = ApplyJobEntity.builder().id(UUID.randomUUID()).build();

    when(candidateRepository.findById(candidateId))
      .thenReturn(Optional.of(CandidateEntity.builder().id(candidateId).build()));

    when(JobRepository.findById(jobId))
      .thenReturn(Optional.of(JobEntity.builder().id(candidateId).build()));

    when(applyJobRepository.save(jobApplication)).thenReturn(createdJobApplication);

    var result = applyJobCandidateUseCase.execute(candidateId, jobId);

    assertThat(result).hasFieldOrProperty("id");
    assertNotNull(result.getId());
  }
}
