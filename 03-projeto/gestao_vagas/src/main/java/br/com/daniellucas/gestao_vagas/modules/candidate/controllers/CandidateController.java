package br.com.daniellucas.gestao_vagas.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.daniellucas.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.daniellucas.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.daniellucas.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.daniellucas.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.daniellucas.gestao_vagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.daniellucas.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.daniellucas.gestao_vagas.modules.company.entities.JobEntity;
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
@Tag(name = "Candidato", description = "Informação pertinentes ao candidato")
public class CandidateController {
  
  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;
  
  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Autowired
  private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

  @Autowired
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;

  @PostMapping("/")
  @Operation(
    summary = "Cadastro de candidato",
    description = "Esta função é responsável por cadastrar um candidato"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200", content = {
      @Content(
        schema = @Schema(implementation = CandidateEntity.class)
      )
    }),
    @ApiResponse(responseCode = "400", content = {
      @Content(
        schema = @Schema(implementation = String.class)
      )
    })
  })
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
  @Operation(
    summary = "Perfil do candidato",
    description = "Esta função é responsável por buscar as informações do perfil do candidato"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200", content = {
      @Content(
        schema = @Schema(implementation = ProfileCandidateResponseDTO.class)
      )
    }),
    @ApiResponse(responseCode = "400", content = {
      @Content(
        schema = @Schema(implementation = String.class)
      )
    })
  })
  @SecurityRequirement(name = "jwt_auth")
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
  @Operation(
    summary = "Listagem de vagas disponíveis para o candidato",
    description = "Esta função é responsável por listar todas as vagas disponíveis, baseada no filtro"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200", content = {
      @Content(
        array = @ArraySchema(schema = @Schema(implementation = JobEntity.class))
      )
    })
  })
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<List<JobEntity>> findJobByFilter(@RequestParam String filter) {
    var result = this.listAllJobsByFilterUseCase.execute(filter);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
  

  @PostMapping("/job/apply")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(
    summary = "Inscrição em uma vaga",
    description = "Esta função é responsável por realizar a inscrição do candidato em uma vaga"
  )
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID jobId) {
    var candidateId = request.getAttribute("sub");
    try {
      var result = this.applyJobCandidateUseCase.execute(UUID.fromString(candidateId.toString()), jobId);

      return ResponseEntity.status(HttpStatus.OK).body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  
}
