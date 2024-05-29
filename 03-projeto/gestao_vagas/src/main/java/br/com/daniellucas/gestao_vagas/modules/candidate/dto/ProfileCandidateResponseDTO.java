package br.com.daniellucas.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {
  @Schema(example = "Desenvolvedor Node")
  private String description;

  @Schema(example = "DanielSLucas")
  private String username;

  @Schema(example = "daniel@email.com")
  private String email;

  private UUID id;

  @Schema(example = "Daniel Lucas")
  private String name;
}
