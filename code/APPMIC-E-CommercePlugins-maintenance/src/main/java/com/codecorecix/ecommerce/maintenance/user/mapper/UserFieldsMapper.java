package com.codecorecix.ecommerce.maintenance.user.mapper;

import java.util.List;

import com.codecorecix.ecommerce.event.entities.User;
import com.codecorecix.ecommerce.maintenance.user.api.dto.request.UserRequestDto;
import com.codecorecix.ecommerce.maintenance.user.api.dto.response.UserResponseDto;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserFieldsMapper {

  User sourceToDestination(final UserRequestDto source);

  UserResponseDto destinationToSource(final User destination);

  List<UserResponseDto> toDto(final List<User> entityList);
}
