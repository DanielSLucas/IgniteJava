package br.com.daniellucas.gestao_vagas.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.daniellucas.gestao_vagas.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private JWTProvider jwtProvider;

  @Override
  protected void doFilterInternal(
    HttpServletRequest request, 
    HttpServletResponse response, 
    FilterChain filterChain
  ) throws ServletException, IOException {
    // SecurityContextHolder.getContext().setAuthentication(null);
    
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    
    if (header != null) {
      var token = this.jwtProvider.validateToken(header);

      if(token == null) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }

      request.setAttribute("sub", token.getSubject());

      var roles = token.getClaim("roles").asList(String.class);
      var grants = roles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())).toList();

      var auth = new UsernamePasswordAuthenticationToken(token.getSubject(), null, grants);

      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    filterChain.doFilter(request, response);
  }

}
