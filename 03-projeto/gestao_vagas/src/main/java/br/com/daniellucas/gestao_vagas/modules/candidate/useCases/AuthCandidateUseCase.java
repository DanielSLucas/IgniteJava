package br.com.daniellucas.gestao_vagas.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.daniellucas.gestao_vagas.exceptions.ResourceNotFoundException;
import br.com.daniellucas.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.daniellucas.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.daniellucas.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.daniellucas.gestao_vagas.providers.JWTProvider;

@Service
public class AuthCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JWTProvider jwtProvider;

  public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
    var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
      .orElseThrow(() -> {
        throw new ResourceNotFoundException();
      });

    var passwordMatches = this.passwordEncoder.matches(
      authCandidateRequestDTO.password(), candidate.getPassword()
    );

    if (!passwordMatches) {
      throw new AuthenticationException();
    }

    var expiration = Instant.now().plus(Duration.ofMinutes(10));

    List<String> roles = Arrays.asList("CANDIDATE");

    var token = this.jwtProvider.generateToken(
      candidate.getId().toString(),
      expiration,
      roles
    );

    return AuthCandidateResponseDTO.builder()
      .access_token(token)
      .expires_in(expiration.toEpochMilli())
      .roles(roles)
      .build();
  }
}
