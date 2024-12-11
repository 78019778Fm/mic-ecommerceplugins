package com.codecorecix.ecommerce.order.info.api.dto.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.codecorecix.ecommerce.order.status.api.dto.request.OrderStatusRequestDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequestDto implements Serializable {

  private Integer id;

  @NotNull(message = "The customerId is null, please fill")
  private Integer customerId;

  private Integer employeeId;

  @NotNull(message = "The orderStatus is null, please fill")
  private OrderStatusRequestDto orderStatus;

  private Double totalAmount;

  private String orderNotes;

  @NotEmpty(message = "The orderDetails is null, please fill")
  private List<OrderDetailRequestDto> orderDetails = new ArrayList<>();
}
