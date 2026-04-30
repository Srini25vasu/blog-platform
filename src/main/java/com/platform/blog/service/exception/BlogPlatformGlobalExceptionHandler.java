package com.platform.blog.service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class BlogPlatformGlobalExceptionHandler {
  @ExceptionHandler(BlogException.class)
  public ResponseEntity<ErrorResponse> handleBlogException(BlogException ex, HttpServletRequest req) {
    var errorResponse = ErrorResponse.builder()
          .errorCode(ex.getErrorCode().name())
          .message(ex.getMessage())
          .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
          .timestamp(Instant.now())
          .path(req.getRequestURI())
          .build();
    return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
