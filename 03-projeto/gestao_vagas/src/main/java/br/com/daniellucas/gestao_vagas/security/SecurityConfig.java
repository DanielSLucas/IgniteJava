package br.com.daniellucas.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
  
  @Autowired
  private SecurityFilter securityFilter;

  private final String[] SWAGGER_URLS = {
    "/swagger-ui/**",
    "/swagger-resources/**",
    "/v3/api-docs/**"
  };

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> {
        auth
          .requestMatchers(SWAGGER_URLS).permitAll()
          .requestMatchers("/actuator/**").permitAll()
          .requestMatchers("/candidates/").permitAll()
          .requestMatchers("/companies/").permitAll()
          .requestMatchers("/auth/company").permitAll()
          .requestMatchers("/auth/candidate").permitAll();
        
        auth.anyRequest().authenticated();  
      })
      .addFilterBefore(securityFilter, BasicAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
