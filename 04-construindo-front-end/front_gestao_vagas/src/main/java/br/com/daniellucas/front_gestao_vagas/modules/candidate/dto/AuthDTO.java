package br.com.daniellucas.front_gestao_vagas.modules.candidate.dto;

import java.util.List;

import lombok.Data;

@Data
public class AuthDTO {
  private String access_token;
  private Long expires_in;
  private List<String> roles;
}
