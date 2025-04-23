package com.codecorecix.ecommerce.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtErrorMessage {
  USERNAME_NOT_FOUND(101, "User not found or response body is null"),
  SERVICE_UNAVAILABLE(102,
      "¡Ups!, The user credentials could not be validated because the maintenance microservice is not responding - Service unavailable."),
  ERROR_INTERNAL(103, "An error has occurred in the JWT microservice."),
  MAL_FORMATTED(104, "User or password has no value or the request properties are not allowed"),
  INVALID_TOKEN(105, "The token is invalid or has been modified");

  private final Integer errorCode;

  private final String errorMessage;
}
