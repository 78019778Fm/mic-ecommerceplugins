package com.codecorecix.ecommerce.order.info.mapper;

import com.codecorecix.ecommerce.event.entities.OrderDetail;
import com.codecorecix.ecommerce.order.info.api.dto.request.OrderDetailRequestDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDetailFieldsMapper {

  @Mapping(target = "order.id", source = "orderId")
  OrderDetail toEntity(final OrderDetailRequestDto source, final Integer orderId);
}
