package com.codecorecix.ecommerce.maintenance.user.service;

import java.util.List;
import java.util.Optional;

import com.codecorecix.ecommerce.event.entities.User;
import com.codecorecix.ecommerce.maintenance.user.api.dto.request.UserRequestDto;
import com.codecorecix.ecommerce.maintenance.user.api.dto.response.UserResponseDto;
import com.codecorecix.ecommerce.maintenance.user.mapper.UserFieldsMapper;
import com.codecorecix.ecommerce.maintenance.user.repository.UserRepository;
import com.codecorecix.ecommerce.maintenance.user.utils.UserConstants;
import com.codecorecix.ecommerce.maintenance.user.utils.UserUtils;
import com.codecorecix.ecommerce.utils.GenericResponse;
import com.codecorecix.ecommerce.utils.GenericResponseConstants;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserFieldsMapper mapper;

  private final UserRepository repository;

  @Override
  public GenericResponse<List<UserResponseDto>> retrieveAllUsers() {
    final List<User> brands = this.repository.findAll();
    return new GenericResponse<>(GenericResponseConstants.RPTA_OK, GenericResponseConstants.CORRECT_OPERATION, this.mapper.toDto(brands));
  }

  @Override
  public GenericResponse<UserResponseDto> create(final UserRequestDto brandRequestDto) {
    final User brand = (this.repository.save(this.mapper.sourceToDestination(brandRequestDto)));
    return new GenericResponse<>(GenericResponseConstants.RPTA_OK, GenericResponseConstants.CORRECT_OPERATION,
        this.mapper.destinationToSource(brand));
  }

  @Override
  public GenericResponse<UserResponseDto> deleteById(final Integer id) {
    final Optional<User> brand = this.repository.findById(id);
    if (brand.isPresent()) {
      this.repository.deleteById(id);
      return new GenericResponse<>(GenericResponseConstants.RPTA_OK, GenericResponseConstants.CORRECT_OPERATION, null);
    } else {
      return new GenericResponse<>(GenericResponseConstants.RPTA_ERROR,
          StringUtils.joinWith(GenericResponseConstants.DASH, GenericResponseConstants.INCORRECT_OPERATION, UserConstants.NO_EXIST),
          null);
    }
  }

  @Override
  @Transactional
  public GenericResponse<UserResponseDto> updateUserStatus(final Boolean isActive, final Integer id) {
    final Optional<User> brandOptional = this.repository.findById(id);
    if (brandOptional.isPresent()) {
      this.repository.disabledOrEnabledUser(isActive, id);
      return new GenericResponse<>(GenericResponseConstants.RPTA_OK, GenericResponseConstants.CORRECT_OPERATION, null);
    } else {
      return new GenericResponse<>(GenericResponseConstants.RPTA_ERROR,
          StringUtils.joinWith(GenericResponseConstants.DASH, GenericResponseConstants.INCORRECT_OPERATION, UserConstants.NO_EXIST),
          null);
    }
  }

  @Override
  public GenericResponse<UserResponseDto> findById(final Integer id) {
    final Optional<User> user = this.repository.findById(id);
    return user.map(value -> UserUtils.buildGenericResponseSuccess(this.mapper.destinationToSource(value)))
        .orElseGet(UserUtils::buildGenericResponseError);
  }
}
