package com.codecorecix.ecommerce.order.status.service;

import java.util.List;

import com.codecorecix.ecommerce.order.status.api.dto.request.OrderStatusRequestDto;
import com.codecorecix.ecommerce.order.status.api.dto.response.OrderStatusResponseDto;
import com.codecorecix.ecommerce.utils.GenericResponse;

public interface OrderStatusService {

  /**
   * Method used to list all status.
   *
   * @return List of OrderStatusResponseDto.
   */
  GenericResponse<List<OrderStatusResponseDto>> getAllStatus();

  /**
   * Method used to save the status.
   *
   * @param orderStatusRequestDto The status request dto.
   * @return List of OrderStatusResponseDto.
   */
  GenericResponse<OrderStatusResponseDto> save(final OrderStatusRequestDto orderStatusRequestDto);

  /**
   * Method used to delete the status.
   *
   * @param id The id of the status.
   * @return List of OrderStatusResponseDto.
   */
  GenericResponse<OrderStatusResponseDto> delete(final Integer id);

  /**
   * Method used to find the status by id.
   *
   * @param id The id of the status.
   * @return List of OrderStatusResponseDto.
   */
  GenericResponse<OrderStatusResponseDto> findById(final Integer id);
}
