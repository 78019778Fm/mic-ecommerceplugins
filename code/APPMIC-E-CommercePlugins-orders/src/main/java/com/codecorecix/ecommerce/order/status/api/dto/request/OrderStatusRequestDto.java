package com.codecorecix.ecommerce.order.status.api.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderStatusRequestDto implements Serializable {

  private Integer id;

  @NotNull(message = "The status name is null, please fill.")
  @NotEmpty(message = "The status name is empty, please fill.")
  private String statusName;

  private Boolean isActive;
}
