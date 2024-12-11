package com.codecorecix.ecommerce.exceptions;

import com.codecorecix.ecommerce.utils.OrderErrorMessage;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderException extends RuntimeException {

  private final OrderErrorMessage errorMessage;

  private final Integer errorCode;

  public OrderException(final OrderErrorMessage errorMessage) {
    super(errorMessage.getErrorMessage());
    this.errorMessage = errorMessage;
    this.errorCode = errorMessage.getErrorCode();
  }
}
