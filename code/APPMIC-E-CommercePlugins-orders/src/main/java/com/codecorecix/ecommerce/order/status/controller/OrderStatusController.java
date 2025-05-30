package com.codecorecix.ecommerce.order.status.controller;

import java.util.List;
import java.util.Objects;

import com.codecorecix.ecommerce.exception.GenericUnprocessableEntityException;
import com.codecorecix.ecommerce.order.status.api.dto.request.OrderStatusRequestDto;
import com.codecorecix.ecommerce.order.status.api.dto.response.OrderStatusResponseDto;
import com.codecorecix.ecommerce.order.status.service.OrderStatusService;
import com.codecorecix.ecommerce.order.status.utils.OrderStatusConstants;
import com.codecorecix.ecommerce.utils.GenericResponse;
import com.codecorecix.ecommerce.utils.OrdersUtils;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/orders/status")
@RequiredArgsConstructor
public class OrderStatusController {

  private final OrderStatusService service;

  @GetMapping
  public ResponseEntity<GenericResponse<List<OrderStatusResponseDto>>> getAllStatus() {
    return ResponseEntity.status(HttpStatus.OK).body(this.service.getAllStatus());
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponse<OrderStatusResponseDto>> getStatusById(@PathVariable(value = "id") final Integer id) {
    final GenericResponse<OrderStatusResponseDto> response = this.service.findById(id);
    if (Objects.nonNull(response.getBody())) {
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @PostMapping
  public ResponseEntity<GenericResponse<OrderStatusResponseDto>> saveStatus(
      @RequestBody final OrderStatusRequestDto orderStatusRequestDto) {
    if (ObjectUtils.isNotEmpty(orderStatusRequestDto.getId())) {
      throw new GenericUnprocessableEntityException(OrderStatusConstants.UNPROCESSABLE_ENTITY_EXCEPTION);
    } else {
      OrdersUtils.validRequestDto(orderStatusRequestDto);
      return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(orderStatusRequestDto));
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenericResponse<OrderStatusResponseDto>> updateStatus(@PathVariable(value = "id") final Integer id,
      @RequestBody final OrderStatusRequestDto orderStatusRequestDto) {
    final GenericResponse<OrderStatusResponseDto> response = this.service.findById(id);
    if (ObjectUtils.isNotEmpty(response.getBody())) {
      orderStatusRequestDto.setId(response.getBody().getId());
      return ResponseEntity.status(HttpStatus.OK).body(this.service.save(orderStatusRequestDto));
    } else {
      OrdersUtils.validRequestDto(orderStatusRequestDto);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse<OrderStatusResponseDto>> deleteStatusById(@PathVariable(value = "id") final Integer id) {
    final GenericResponse<OrderStatusResponseDto> response = this.service.findById(id);
    if (ObjectUtils.isNotEmpty(response.getBody())) {
      return ResponseEntity.status(HttpStatus.OK).body(this.service.delete(response.getBody().getId()));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }
}
