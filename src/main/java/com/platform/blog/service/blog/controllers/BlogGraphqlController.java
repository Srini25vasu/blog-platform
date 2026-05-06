package com.platform.blog.service.blog.controllers;


import com.platform.blog.service.blog.BlogService;
import com.platform.blog.service.blog.dto.BlogDto;
import lombok.AllArgsConstructor;
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
    return blogService.getAllBlogs();
  }

  @QueryMapping
  public BlogDto blog(String id) {
    return blogService.getBlogById(id);
  }

  @MutationMapping
  public BlogDto saveBlog(BlogDto blogDto) {
    return blogService.saveBlog(blogDto);
  }
}
