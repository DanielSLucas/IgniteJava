package br.com.daniellucas.gestao_vagas.modules.candidate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.daniellucas.gestao_vagas.modules.candidate.CandidateEntity;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/candidates")
public class CandidateController {
  

  @PostMapping("/")
  public ResponseEntity<CandidateEntity> create(@Valid @RequestBody CandidateEntity candidateEntity) {  
    return ResponseEntity.status(HttpStatus.CREATED).body(candidateEntity);
  }
  
}
