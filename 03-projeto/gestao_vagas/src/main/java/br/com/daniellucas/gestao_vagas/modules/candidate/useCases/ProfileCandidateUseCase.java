package br.com.daniellucas.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.daniellucas.gestao_vagas.exceptions.ResourceNotFoundException;
import br.com.daniellucas.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.daniellucas.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;

@Service
public class ProfileCandidateUseCase {
  @Autowired
  private CandidateRepository candidateRepository;

  public ProfileCandidateResponseDTO execute(UUID candidateId) {
    var candidate = this.candidateRepository.findById(candidateId)
      .orElseThrow(() -> {
        throw new ResourceNotFoundException();
      });

    return ProfileCandidateResponseDTO.builder()
      .description(candidate.getDescription())
      .email(candidate.getEmail())
      .id(candidate.getId())
      .name(candidate.getName())
      .username(candidate.getUsername())
      .build();
  }
}
