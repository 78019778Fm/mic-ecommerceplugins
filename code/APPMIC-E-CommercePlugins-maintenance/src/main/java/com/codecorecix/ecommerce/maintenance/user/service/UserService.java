package com.codecorecix.ecommerce.maintenance.user.service;

import java.util.List;

import com.codecorecix.ecommerce.maintenance.user.api.dto.request.UserRequestDto;
import com.codecorecix.ecommerce.maintenance.user.api.dto.response.UserResponseDto;
import com.codecorecix.ecommerce.utils.GenericResponse;

public interface UserService {

  /**
   * Method used to list all users.
   *
   * @return List of UserResponseDto.
   */
  GenericResponse<List<UserResponseDto>> retrieveAllUsers();

  /**
   * Method used to save the users.
   *
   * @param userRequestDto The user request dto.
   * @return List of UserResponseDto.
   */
  GenericResponse<UserResponseDto> create(final UserRequestDto userRequestDto);

  /**
   * Method used to delete the user.
   *
   * @param id The id of the user.
   * @return List of UserResponseDto.
   */
  GenericResponse<UserResponseDto> deleteById(final Integer id);

  /**
   * Method used to update the status of the user.
   *
   * @param isActive True if is active or false en otherwise.
   * @return List of UserResponseDto.
   */
  GenericResponse<UserResponseDto> updateUserStatus(final Boolean isActive, final Integer id);

  /**
   * Method used to find the user by id.
   *
   * @param id The id of the user.
   * @return List of UserResponseDto.
   */
  GenericResponse<UserResponseDto> findById(final Integer id);
}
