package br.com.daniellucas.gestao_vagas.modules.company.useCases;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.daniellucas.gestao_vagas.exceptions.ResourceNotFoundException;
import br.com.daniellucas.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.daniellucas.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.daniellucas.providers.JWTProvider;

@Service
public class AuthCompanyUseCase {
  
  @Autowired
  private JWTProvider jwtProvider;
  
  @Autowired
  private CompanyRepository companyRepository;
  
  @Autowired
  private PasswordEncoder passwordEncoder;

  public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
    var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername())
      .orElseThrow(() -> {
        throw new ResourceNotFoundException();
      });

    var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

    if (!passwordMatches) {
      throw new AuthenticationException();
    }

    var token = this.jwtProvider.generateToken(company.getId().toString());

    return token;
  }
}
