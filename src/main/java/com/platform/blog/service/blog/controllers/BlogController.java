package com.platform.blog.service.blog.controllers;

import com.platform.blog.service.blog.BlogService;
import com.platform.blog.service.blog.dto.BlogDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/blogs")
@AllArgsConstructor
public class BlogController {
  private final BlogService blogService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BlogDto saveBlog(@Valid @RequestBody BlogDto blogDto) {
    return blogService.saveBlog(blogDto);
  }
}
