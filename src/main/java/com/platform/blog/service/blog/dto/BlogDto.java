package com.platform.blog.service.blog.dto;

import lombok.Builder;

@Builder
public record BlogDto(
      String slug,
      String title,
      String content,
      String author,
      String description
) {
}
