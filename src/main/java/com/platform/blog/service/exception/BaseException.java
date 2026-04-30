package com.platform.blog.service.exception;

import com.platform.blog.service.enums.ErrorCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{
  private final ErrorCode errorCode;
  private final String message;

  private final String detail;

  public BaseException(ErrorCode errorCode, String message, String detail) {
    super(message);
    this.errorCode = errorCode;
    this.message = message;
    this.detail = detail;
  }
}
