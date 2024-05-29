package br.com.daniellucas.gestao_vagas.modules.candidate.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.daniellucas.gestao_vagas.modules.candidate.entities.CandidateEntity;

@Repository
public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {
  Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);  
  Optional<CandidateEntity> findByUsername(String username);
}
