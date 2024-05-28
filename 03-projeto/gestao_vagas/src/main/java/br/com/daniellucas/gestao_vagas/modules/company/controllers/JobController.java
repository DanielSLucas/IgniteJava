package br.com.daniellucas.gestao_vagas.modules.company.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.daniellucas.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.daniellucas.gestao_vagas.modules.company.entities.JobEntity;
import br.com.daniellucas.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/jobs")
public class JobController {
  
  @Autowired
  private CreateJobUseCase createJobUseCase;

  @PostMapping("/")
  public ResponseEntity<JobEntity> postMethodName(
    @Valid @RequestBody CreateJobDTO createJobDTO,
    HttpServletRequest request
  ) {
    var companyId = request.getAttribute("sub");

    var jobEntity = JobEntity.builder()
      .benefits(createJobDTO.getBenefits())
      .description(createJobDTO.getDescription())
      .level(createJobDTO.getLevel())
      .companyId(UUID.fromString(companyId.toString()))
      .build();

    var entity = this.createJobUseCase.execute(jobEntity);

    return ResponseEntity.status(HttpStatus.CREATED).body(entity);
  }
  
}
