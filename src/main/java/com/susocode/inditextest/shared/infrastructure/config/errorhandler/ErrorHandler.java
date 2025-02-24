package com.susocode.inditextest.shared.infrastructure.config.errorhandler;

import com.susocode.inditextest.model.ErrorResponseDto;
import com.susocode.inditextest.shared.exception.BaseException;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(value = {BaseException.class})
  public Mono<ResponseEntity<ErrorResponseDto>> handleBaseException(BaseException ex) {
    var error = new ErrorResponseDto(ex.getKey(), ex.getMessage(), LocalDateTime.now());
    return Mono.just(ResponseEntity
        .status(ex.getStatusCode())
        .body(error));
  }

  @ExceptionHandler(TypeMismatchException.class)
  public Mono<ResponseEntity<ErrorResponseDto>> handleTypeMismatchException(
      TypeMismatchException ex) {
    var requiredType = Objects.toString(ex.getRequiredType(), "unknown");
    var message = String.format(
        "The parameter '%s' with value '%s' could not be converted to type '%s'.",
        ex.getPropertyName(), ex.getValue(), requiredType);
    var error = new ErrorResponseDto(
        "TYPE_MISMATCH_ERROR",
        message,
        LocalDateTime.now());
    return Mono.just(ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(error));
  }

}
