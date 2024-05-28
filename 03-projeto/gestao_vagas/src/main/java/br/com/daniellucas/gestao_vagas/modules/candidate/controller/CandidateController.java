package br.com.daniellucas.gestao_vagas.modules.candidate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.daniellucas.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.daniellucas.gestao_vagas.modules.candidate.CandidateRepository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/candidates")
public class CandidateController {
  
  @Autowired
  private CandidateRepository candidateRepository;

  @PostMapping("/")
  public ResponseEntity<CandidateEntity> create(@Valid @RequestBody CandidateEntity candidateEntity) {  
    var entity =this.candidateRepository.save(candidateEntity);

    return ResponseEntity.status(HttpStatus.CREATED).body(entity);
  }
  
}
