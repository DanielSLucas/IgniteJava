package br.com.daniellucas.gestao_vagas.modules.company.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.daniellucas.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.daniellucas.gestao_vagas.modules.company.entities.JobEntity;
import br.com.daniellucas.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/jobs")
public class JobController {
  
  @Autowired
  private CreateJobUseCase createJobUseCase;

  @PostMapping("/")
  @PreAuthorize("hasRole('COMPANY')")
  @Tag(name = "Vagas", description = "Informações das vagas")
  @Operation(
    summary = "Cadastro de vaga",
    description = "Esta função é responsável por cadastrar as vagas da empresa"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200", content = {
      @Content(
        schema = @Schema(implementation = JobEntity.class)
      )
    })
  })
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<JobEntity> createJob(
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
