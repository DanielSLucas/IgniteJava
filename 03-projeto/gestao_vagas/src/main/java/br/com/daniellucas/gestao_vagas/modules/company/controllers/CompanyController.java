package br.com.daniellucas.gestao_vagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.daniellucas.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.daniellucas.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/companies")
public class CompanyController {
  @Autowired
  private CreateCompanyUseCase createCompanyUseCase;

  @PostMapping("/")
  public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity) {
    try {
      var entity = this.createCompanyUseCase.execute(companyEntity);

      return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
