package br.com.daniellucas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/primeiraController")
public class PrimeiraController {
  @GetMapping("/primeiroMetodo/{id}")
  public String primeiroMetodo(@PathVariable String id) {
    return "O parametro e: " + id;
  }
  
  @GetMapping("/metodoComQueryParams")
  public String metodoComQueryParams(@RequestParam String id) {
    return "O parametro com metodoComQueryParams e: " + id;
  }

  @GetMapping("/metodoComQueryParams2")
  public String metodoComQueryParams2(@RequestParam Map<String, String> allParams) {
    return "O parametro com metodoComQueryParams2 e: " + allParams.entrySet();
  }
}
