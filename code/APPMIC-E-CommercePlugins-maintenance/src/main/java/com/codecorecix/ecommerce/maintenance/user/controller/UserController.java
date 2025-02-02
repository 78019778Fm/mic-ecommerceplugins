package com.codecorecix.ecommerce.maintenance.user.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.codecorecix.ecommerce.exception.GenericUnprocessableEntityException;
import com.codecorecix.ecommerce.maintenance.user.api.dto.request.UserRequestDto;
import com.codecorecix.ecommerce.maintenance.user.api.dto.response.UserResponseDto;
import com.codecorecix.ecommerce.maintenance.user.service.UserService;
import com.codecorecix.ecommerce.maintenance.user.utils.UserConstants;
import com.codecorecix.ecommerce.utils.GenericResponse;
import com.codecorecix.ecommerce.utils.GenericUtils;
import com.codecorecix.ecommerce.utils.MaintenanceUtils;

import jakarta.validation.Valid;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<GenericResponse<List<UserResponseDto>>> retrieveAllUsers() {
    return ResponseEntity.status(HttpStatus.OK).body(this.service.retrieveAllUsers());
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponse<UserResponseDto>> retrieveUserById(@PathVariable(value = "id") final Integer id) {
    final GenericResponse<UserResponseDto> response = this.service.findById(id);
    if (Objects.nonNull(response.getBody())) {
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @PostMapping
  public ResponseEntity<GenericResponse<UserResponseDto>> createUser(@RequestBody final UserRequestDto userRequestDto) {
    if (ObjectUtils.isNotEmpty(userRequestDto.getId())) {
      throw new GenericUnprocessableEntityException(UserConstants.UNPROCESSABLE_ENTITY_EXCEPTION);
    } else {
      MaintenanceUtils.validRequestDto(userRequestDto);
      return ResponseEntity.status(HttpStatus.CREATED).body(this.service.create(userRequestDto));
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenericResponse<UserResponseDto>> updateUser(@Valid @PathVariable(value = "id") final Integer id,
      @RequestBody final UserRequestDto userRequestDto) {
    final GenericResponse<UserResponseDto> response = this.service.findById(id);
    if (ObjectUtils.isNotEmpty(response.getBody())) {
      userRequestDto.setId(id);
      MaintenanceUtils.validRequestDto(userRequestDto);
      return ResponseEntity.status(HttpStatus.OK).body(this.service.create(userRequestDto));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<GenericResponse<UserResponseDto>> updateUserStatus(@PathVariable(value = "id") final Integer id,
      @RequestParam(value = "isActive") final Boolean isActive) {
    final GenericResponse<UserResponseDto> response = this.service.findById(id);
    if (ObjectUtils.isNotEmpty(response.getBody())) {
      return ResponseEntity.status(HttpStatus.OK).body(this.service.updateUserStatus(isActive, id));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse<UserResponseDto>> deleteUser(@PathVariable(value = "id") final Integer id) {
    final GenericResponse<UserResponseDto> response = this.service.findById(id);
    if (ObjectUtils.isNotEmpty(response.getBody())) {
      return ResponseEntity.status(HttpStatus.OK).body(this.service.deleteById(id));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @GetMapping("/authorized")
  public ResponseEntity<GenericResponse<Map<String, Object>>> retrieveAuthorizedUsers(@RequestParam(name = "code") final String code) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(GenericUtils.buildGenericResponseSuccess(StringUtils.EMPTY, Collections.singletonMap("code", code)));
  }
}
