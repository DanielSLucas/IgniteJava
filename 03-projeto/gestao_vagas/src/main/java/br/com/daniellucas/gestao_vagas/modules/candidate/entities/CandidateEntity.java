package br.com.daniellucas.gestao_vagas.modules.candidate.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "candidates")
public class CandidateEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  
  @Schema(example = "Daniel Lucas", requiredMode = RequiredMode.REQUIRED, description = "Nome do candidato")
  private String name;

  @NotBlank(message = "O campo [username] é obrigatório")
  @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaços")
  @Schema(example = "DanielSLucas", requiredMode = RequiredMode.REQUIRED, description = "Username do candidato")
  private String username;

  @NotBlank(message = "O campo [email] é obrigatório")
  @Email(message = "O campo [email] deve conter um email valido")
  @Schema(example = "daniel@email.com", requiredMode = RequiredMode.REQUIRED, description = "Email do candidato")
  private String email;

  @NotBlank(message = "O campo [password] é obrigatório")
  @Length(min = 10, max = 100, message = "O campo [password] deve ter de 10 a 100 caracteres")
  @Schema(example = "0123456789", minLength = 10, maxLength = 100, requiredMode = RequiredMode.REQUIRED, description = "Senha do candidato")
  private String password;
  @Schema(example = "Dev node com um pezinho no java", description = "Breve descrição do candidato")
  private String description;
  private String curriculum;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
