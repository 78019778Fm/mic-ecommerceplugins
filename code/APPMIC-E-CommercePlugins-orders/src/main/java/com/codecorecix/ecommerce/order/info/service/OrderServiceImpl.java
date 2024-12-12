package com.codecorecix.ecommerce.order.info.service;

import java.time.LocalDateTime;
import java.util.List;

import com.codecorecix.ecommerce.event.clients.MaintenanceClientRest;
import com.codecorecix.ecommerce.event.entities.Order;
import com.codecorecix.ecommerce.event.models.ProductInfo;
import com.codecorecix.ecommerce.exceptions.OrderException;
import com.codecorecix.ecommerce.order.info.api.dto.request.OrderDetailRequestDto;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  private final OrderFieldsMapper orderFieldsMapper;

  private final MaintenanceClientRest maintenanceClientRest;

  private final OrderStatusService orderStatusService;

  @Override
  public GenericResponse<OrderResponseDto> saveOrder(final OrderRequestDto orderRequestDto) {
    try {
      final Order orderInfo = this.orderFieldsMapper.sourceToDestination(orderRequestDto);
      orderInfo.getOrderDetails().forEach(detail -> detail.setOrder(orderInfo));
      orderInfo.setOrderDate(LocalDateTime.now());
      GenericResponse<List<ProductInfo>> response = this.maintenanceClientRest.checkProducts(
          orderRequestDto.getOrderDetails().stream().map(OrderDetailRequestDto::getProductId).toList());
      final GenericResponse<OrderStatusResponseDto> findStatusById =
          this.orderStatusService.findById(orderRequestDto.getOrderStatus().getId());
      if (findStatusById.getRpta().equals(-1)) {
        throw new OrderException(OrderErrorMessage.ERROR_RESOURCE_NOT_AVAILABLE);
      }
      final Order orderBD = this.orderRepository.save(orderInfo);
      final OrderResponseDto orderResponseDto = this.orderFieldsMapper.destinationToSource(orderBD);
      return new GenericResponse<>(GenericResponseConstants.RPTA_OK, GenericResponseConstants.CORRECT_OPERATION, orderResponseDto);
    } catch (final FeignException e) {
      return new GenericResponse<>(GenericResponseConstants.RPTA_ERROR, OrderConstants.UNAVAILABLE_SERVICE_MAINTENANCE, null);
    } catch (Exception e) {
      return new GenericResponse<>(GenericResponseConstants.RPTA_ERROR, GenericResponseConstants.WRONG_OPERATION, null);
    }
  }
}
