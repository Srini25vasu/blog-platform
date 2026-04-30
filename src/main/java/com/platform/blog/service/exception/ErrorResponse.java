package com.platform.blog.service.exception;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ErrorResponse {
  private String errorCode;
  private String message;
  private int status;
  private Instant timestamp;
  private String path;
}
