package com.codecorecix.ecommerce.utils;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MaintenanceUtils {

  /**
   * Méthod for valid the object request dto.
   *
   * @param object the object.
   */
  public static <T> void validRequestDto(final T object) {
    final Validator validator;
    try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      validator = factory.getValidator();
    }
    final Set<ConstraintViolation<Object>> violations = validator.validate(object);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
