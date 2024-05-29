package br.com.daniellucas.gestao_vagas.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.daniellucas.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.daniellucas.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.daniellucas.gestao_vagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.daniellucas.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.daniellucas.gestao_vagas.modules.company.entities.JobEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/candidates")
public class CandidateController {
  
  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;
  
  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Autowired
  private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;


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
  @PreAuthorize("hasRole('CANDIDATE')")
  public ResponseEntity<Object> getProfile(HttpServletRequest request) {
    var candidateId = request.getAttribute("sub");

    try {
      var entity = this.profileCandidateUseCase.execute(UUID.fromString(candidateId.toString()));

      return ResponseEntity.status(HttpStatus.OK).body(entity);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  
  @GetMapping("/jobs")
  @PreAuthorize("hasRole('CANDIDATE')")
  public ResponseEntity<List<JobEntity>> findJobByFilter(@RequestParam String filter) {
    var result = this.listAllJobsByFilterUseCase.execute(filter);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
  
}
