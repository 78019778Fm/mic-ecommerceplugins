package com.codecorecix.ecommerce.exceptions;

import com.codecorecix.ecommerce.utils.MaintenanceErrorMessage;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MaintenanceException extends RuntimeException {

  private final MaintenanceErrorMessage errorMessage;

  private final Integer errorCode;

  public MaintenanceException(final MaintenanceErrorMessage errorMessage) {
    super(errorMessage.getErrorMessage());
    this.errorMessage = errorMessage;
    this.errorCode = errorMessage.getErrorCode();
  }
}
