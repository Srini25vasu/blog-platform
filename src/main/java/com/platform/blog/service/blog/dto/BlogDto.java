package com.platform.blog.service.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import tools.jackson.databind.JsonNode;

@Builder
public record BlogDto(

      @NotBlank(message = "Slug must not be blank")
      @Size(max = 255, message = "Slug must not exceed 255 characters")
      String slug,

      @NotBlank(message = "Title must not be blank")
      @Size(max = 255, message = "Title must not exceed 255 characters")
      String title,

      @NotNull(message = "Content must not be null")
      JsonNode content,

      String author,

      @Size(max = 1000, message = "Description must not exceed 1000 characters")
      String description
) {
}
