package com.codecorecix.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @RequestMapping("/")
  public String home() {
    return "The microservice APPMIC-E-CommercePlugins-jwt has been activated";
  }
}
