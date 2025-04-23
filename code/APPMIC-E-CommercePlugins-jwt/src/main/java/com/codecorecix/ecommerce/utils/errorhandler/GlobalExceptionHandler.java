package com.codecorecix.ecommerce.utils.errorhandler;

import com.codecorecix.ecommerce.exceptions.JwtException;
import com.codecorecix.ecommerce.utils.GenericResponse;
import com.codecorecix.ecommerce.utils.GenericResponseConstants;
import com.codecorecix.ecommerce.utils.JwtErrorMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  public record ErrorResponse(Integer code, String message) {

  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
  public GenericResponse<Object> exception(final Exception ex) {
    return new GenericResponse<>(GenericResponseConstants.RPTA_ERROR, GenericResponseConstants.WRONG_OPERATION, ex.getMessage());
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public GenericResponse<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex) {
    return new GenericResponse<>(GenericResponseConstants.RPTA_ERROR,
        StringUtils.joinWith(GenericResponseConstants.COLON, GenericResponseConstants.WRONG_OPERATION, ex.getRequestURL()),
        ex.getMessage());
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
  public GenericResponse<Object> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException ex) {
    return new GenericResponse<>(GenericResponseConstants.RPTA_ERROR,
        StringUtils.joinWith(GenericResponseConstants.COLON, GenericResponseConstants.WRONG_OPERATION, "The method is not allowed"),
        ex.getMessage());
  }

  @ExceptionHandler(JwtException.class)
  public ResponseEntity<GenericResponse<Object>> handleMaintenanceException(final JwtException ex) {
    JwtErrorMessage errorMessage = ex.getErrorMessage();
    HttpStatus status = switch (errorMessage) {
      case USERNAME_NOT_FOUND -> HttpStatus.NOT_FOUND;
      case SERVICE_UNAVAILABLE -> HttpStatus.SERVICE_UNAVAILABLE;
      case ERROR_INTERNAL -> HttpStatus.INTERNAL_SERVER_ERROR;
      case MAL_FORMATTED -> HttpStatus.BAD_REQUEST;
      case INVALID_TOKEN -> HttpStatus.UNAUTHORIZED;
    };
    return new ResponseEntity<>(new GenericResponse<>(GenericResponseConstants.RPTA_ERROR, GenericResponseConstants.WRONG_OPERATION,
        new ErrorResponse(ex.getErrorCode(), errorMessage.getErrorMessage())), status);
  }
}
