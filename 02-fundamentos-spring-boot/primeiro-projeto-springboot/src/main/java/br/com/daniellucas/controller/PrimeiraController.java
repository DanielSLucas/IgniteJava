package br.com.daniellucas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/primeiraController")
public class PrimeiraController {
  @GetMapping("/primeiroMetodo")
  public String primeiroMetodo() {
    return "Mue primiero metodo no spring";
  }
  
}
