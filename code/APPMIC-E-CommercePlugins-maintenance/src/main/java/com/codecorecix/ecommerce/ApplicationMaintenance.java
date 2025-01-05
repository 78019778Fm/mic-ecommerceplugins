package com.codecorecix.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ApplicationMaintenance {

  public static void main(String[] args) {
    SpringApplication.run(ApplicationMaintenance.class, args);
  }

}
