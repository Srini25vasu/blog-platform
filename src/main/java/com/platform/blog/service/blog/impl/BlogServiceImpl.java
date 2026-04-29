package com.platform.blog.service.blog.impl;

import com.platform.blog.service.blog.Blog;
import com.platform.blog.service.blog.BlogService;
import com.platform.blog.service.blog.dto.BlogDto;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BlogServiceImpl implements BlogService{
  @Override
  public BlogDto saveBlog(BlogDto blog) {
    return null;
  }

  @Override
  public BlogDto updateBlog(BlogDto blog) {
    return null;
  }

  @Override
  public BlogDto deleteBlog(BlogDto blog) {
    return null;
  }

  @Override
  public BlogDto getBlogById(Long id) {
    return null;
  }

  @Override
  public List<BlogDto> getAllBlogs() {
    return List.of();
  }

  @PostConstruct
  public void log() {
    log.info("BlogServiceImpl initialized");
  }
}
