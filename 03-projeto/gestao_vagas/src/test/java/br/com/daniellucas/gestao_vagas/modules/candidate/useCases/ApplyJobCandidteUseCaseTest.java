package br.com.daniellucas.gestao_vagas.modules.candidate.useCases;

import static org.assertj.core.api.Assertions.assertThat;
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
import br.com.daniellucas.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.daniellucas.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.daniellucas.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidteUseCaseTest {

  @InjectMocks
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private JobRepository JobRepository;

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
}
