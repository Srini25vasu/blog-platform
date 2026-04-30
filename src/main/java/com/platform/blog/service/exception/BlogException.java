package com.platform.blog.service.exception;

import com.platform.blog.service.enums.ErrorCode;
import lombok.Getter;

@Getter
public class BlogException extends BaseException {

  public BlogException(ErrorCode errorCode, String message, String detail) {
    super(errorCode, message, detail);
  }

  public static BlogException saveFailed(String detail) {
    return new BlogException(ErrorCode.TRANSACTION_ERROR, "Failed to save blog", detail);
  }

  public static BlogException notFound(Long id) {
    return new BlogException(ErrorCode.RESOURCE_NOT_FOUND, "Blog not found", "No blog exists with id: " + id);
  }

  public static BlogException invalidInput(String detail) {
    return new BlogException(ErrorCode.BUSINESS_ERROR, "Invalid blog input", detail);
  }
}

