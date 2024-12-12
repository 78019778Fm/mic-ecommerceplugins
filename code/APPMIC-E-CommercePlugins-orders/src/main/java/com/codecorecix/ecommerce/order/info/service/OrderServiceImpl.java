package com.codecorecix.ecommerce.order.info.service;

import java.time.LocalDateTime;

import com.codecorecix.ecommerce.event.entities.Order;
import com.codecorecix.ecommerce.exceptions.OrderException;
import com.codecorecix.ecommerce.order.info.api.dto.request.OrderRequestDto;
import com.codecorecix.ecommerce.order.info.api.dto.response.OrderResponseDto;
import com.codecorecix.ecommerce.order.info.mapper.OrderFieldsMapper;
import com.codecorecix.ecommerce.order.info.repository.OrderRepository;
import com.codecorecix.ecommerce.order.info.utils.OrderConstants;
import com.codecorecix.ecommerce.order.status.api.dto.response.OrderStatusResponseDto;
import com.codecorecix.ecommerce.order.status.service.OrderStatusService;
import com.codecorecix.ecommerce.utils.GenericResponse;
import com.codecorecix.ecommerce.utils.GenericResponseConstants;
import com.codecorecix.ecommerce.utils.OrderErrorMessage;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  private final OrderFieldsMapper orderFieldsMapper;

  private final OrderStatusService orderStatusService;

  private final OrderDetailService orderDetailService;

  @Override
  @Transactional
  public GenericResponse<OrderResponseDto> saveOrder(final OrderRequestDto orderRequestDto) {
    try {
      final Order orderInfo = this.orderFieldsMapper.sourceToDestination(orderRequestDto);
      orderInfo.setOrderDate(LocalDateTime.now());
      final GenericResponse<OrderStatusResponseDto> findStatusById =
          this.orderStatusService.findById(orderRequestDto.getOrderStatus().getId());
      if (findStatusById.getRpta().equals(-1)) {
        throw new OrderException(OrderErrorMessage.ERROR_RESOURCE_NOT_AVAILABLE);
      }
      final Order orderBD = this.orderRepository.save(orderInfo);
      this.orderDetailService.saveOrderDetails(orderRequestDto.getOrderDetails(), orderBD.getId());
      final OrderResponseDto orderResponseDto =
          this.orderFieldsMapper.destinationToSource(orderBD, findStatusById.getBody().getStatusName());
      return new GenericResponse<>(GenericResponseConstants.RPTA_OK, GenericResponseConstants.CORRECT_OPERATION, orderResponseDto);
    } catch (final FeignException e) {
      return new GenericResponse<>(GenericResponseConstants.RPTA_ERROR, OrderConstants.UNAVAILABLE_SERVICE_MAINTENANCE, null);
    }
  }
}
