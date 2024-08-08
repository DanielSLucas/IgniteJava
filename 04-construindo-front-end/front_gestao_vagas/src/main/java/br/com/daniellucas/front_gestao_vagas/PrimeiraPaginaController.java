package br.com.daniellucas.front_gestao_vagas;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PrimeiraPaginaController {
  @GetMapping("/home")
  public String primeiraPaginaHtml(Model model) {
    var msg = "Primeira página construída com thymeleeeeaf";
    model.addAttribute("mensagemDaController", msg);
    return "primeiraPagina";
  }
}
