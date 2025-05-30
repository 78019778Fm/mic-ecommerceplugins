package com.codecorecix.ecommerce.order.info.mapper;

import java.util.List;

import com.codecorecix.ecommerce.event.entities.OrderDetail;
import com.codecorecix.ecommerce.order.info.api.dto.request.OrderDetailRequestDto;
import com.codecorecix.ecommerce.order.info.api.dto.response.OrderDetailResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDetailFieldsMapper {

  @Mapping(target = "order.id", source = "orderId")
  @Mapping(target = "totalPrice", expression = "java(source.getQuantity() * source.getUnitPrice())")
  OrderDetail toEntity(final OrderDetailRequestDto source, final Integer orderId);

  List<OrderDetailResponseDto> toDto(final List<OrderDetail> orderDetails);
}
