package com.platform.blog.service.blog.controllers;


import com.platform.blog.service.blog.BlogService;
import com.platform.blog.service.blog.dto.BlogDto;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class BlogGraphqlController {
  private final BlogService blogService;

  @QueryMapping
  public List<BlogDto> blogs() {
    return getAllBlogs();
  }

  private List<BlogDto> getAllBlogs() {
    return blogService.getAllBlogs();
  }

  @QueryMapping
  public BlogDto blog(@Argument String id) {
    return blogService.getBlogById(id);
  }

  @MutationMapping
  public BlogDto saveBlog(@Argument BlogDto blogDto) {
    return blogService.saveBlog(blogDto);
  }
}
