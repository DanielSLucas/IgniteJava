package br.com.daniellucas.front_gestao_vagas;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/")
public class PrimeiraPaginaController {
  @GetMapping("/home")
  public String primeiraPaginaHtml(Model model) {
    var msg = "Primeira página construída com thymeleeeeaf";
    model.addAttribute("mensagemDaController", msg);
    return "primeiraPagina";
  }

  @GetMapping("/login")
  public String login(Model model, @RequestParam String name) {
    model.addAttribute("nome_do_candidato", name);
    return "/candidate/login";
  }
  

  @PostMapping("/create")
  public String createCandidate(Model model, Pessoa pessoa) {
    return "redirect:/login?name=" + pessoa.name;
  }
  
  record Pessoa(String user, String email, String name) {}
}
