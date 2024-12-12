package com.codecorecix.ecommerce.order.info.service;

import java.util.List;

import com.codecorecix.ecommerce.order.info.api.dto.request.OrderDetailRequestDto;

public interface OrderDetailService {

  /**
   * Method used to save the order details.
   */
  void saveOrderDetails(final List<OrderDetailRequestDto> orderDetailRequestDto, final Integer orderId);
}
