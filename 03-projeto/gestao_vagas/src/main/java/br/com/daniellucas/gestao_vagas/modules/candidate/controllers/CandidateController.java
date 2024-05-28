package br.com.daniellucas.gestao_vagas.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.daniellucas.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.daniellucas.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.daniellucas.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/candidates")
public class CandidateController {
  
  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;
  
  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;


  @PostMapping("/")
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {  
    try {
      var entity = this.createCandidateUseCase.execute(candidateEntity);

      return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  
  @GetMapping("/me")
  public ResponseEntity<Object> getProfile(HttpServletRequest request) {
    var candidateId = request.getAttribute("sub");

    try {
      var entity = this.profileCandidateUseCase.execute(UUID.fromString(candidateId.toString()));

      return ResponseEntity.status(HttpStatus.OK).body(entity);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  
}
