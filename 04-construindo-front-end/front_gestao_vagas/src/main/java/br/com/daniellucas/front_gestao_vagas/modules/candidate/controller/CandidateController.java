package br.com.daniellucas.front_gestao_vagas.modules.candidate.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.daniellucas.front_gestao_vagas.modules.candidate.service.CandidateService;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/candidate")
public class CandidateController {
  
  @Autowired
  private CandidateService candidateService;

  @GetMapping("/login")
  public String login() {
    return "candidate/login";
  }

  @PostMapping("/signIn")
  public String signIn(RedirectAttributes redirectAttributes, HttpSession session, String username, String password) {
    try {
      var authResponse = this.candidateService.login(username, password);

      List<SimpleGrantedAuthority> grants = authResponse.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_"+role.toString().toUpperCase()))
        .toList();

      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(null, null, grants);
      auth.setDetails(authResponse);

      SecurityContextHolder.getContext().setAuthentication(auth);
      SecurityContext securityContext = SecurityContextHolder.getContext();
      session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
      session.setAttribute("token", authResponse);

      return "redirect:/candidate/profile";
    } catch (HttpClientErrorException e) {
      System.out.println(e);
      redirectAttributes.addFlashAttribute("error_message", "Usuário/senha incorretos");
      return "redirect:/candidate/login";
    }
  }
  
  @GetMapping("/profile")
  @PreAuthorize("hasRole('CANDIDATE')")
  public String profile() {
    return "/candidate/profile";
  }

}
