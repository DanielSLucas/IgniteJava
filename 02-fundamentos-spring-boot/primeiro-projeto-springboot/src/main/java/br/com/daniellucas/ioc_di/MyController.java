package br.com.daniellucas.ioc_di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/component")
public class MyController {
  
  @Autowired
  MyComponent myComponent;

  @GetMapping("/doSomething")
  public String doSomething() {
    return myComponent.doSomething();
  }
}
