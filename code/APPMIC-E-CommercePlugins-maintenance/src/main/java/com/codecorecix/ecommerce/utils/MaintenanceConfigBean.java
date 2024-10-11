package com.codecorecix.ecommerce.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component()
@Getter
public class MaintenanceConfigBean {

  @Value("${maintenance.drive.credentials}")
  private String credentialsPath;
}
