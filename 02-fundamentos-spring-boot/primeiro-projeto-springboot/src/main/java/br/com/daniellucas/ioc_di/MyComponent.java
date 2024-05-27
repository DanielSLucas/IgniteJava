package br.com.daniellucas.ioc_di;

import org.springframework.stereotype.Component;

@Component
public class MyComponent {
  public String doSomething() {
    return "Doing something";
  }
}
