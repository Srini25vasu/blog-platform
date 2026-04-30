package com.platform.blog.service.blog.impl;

import com.platform.blog.service.blog.Blog;
import com.platform.blog.service.blog.BlogService;
import com.platform.blog.service.blog.dto.BlogDto;
import com.platform.blog.service.blog.mapper.BlogMapper;
import com.platform.blog.service.blog.persistence.BlogRepository;
import com.platform.blog.service.exception.BlogException;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {

  private final BlogRepository blogRepository;
  private final BlogMapper blogMapper;

  @Override
  public BlogDto saveBlog(BlogDto blogDto) {
    log.info("Blog recieved: {}", blogDto);
    if (blogDto == null) {
      throw BlogException.invalidInput("BlogDto must not be null");
    }
    try {
      Blog blogEntity = blogMapper.toBlog(blogDto);
      Blog saved = blogRepository.save(blogEntity);
      log.info("Blog saved successfully with id: {}", saved.getId());
      return blogMapper.toBlogDto(saved);
    } catch (DataAccessException ex) {
      log.error("Database error while saving blog: {}", ex.getMessage(), ex);
      throw BlogException.saveFailed(ex.getMostSpecificCause().getMessage());
    } catch (Exception ex) {
      log.error("Unexpected error while saving blog: {}", ex.getMessage(), ex);
      throw BlogException.saveFailed(ex.getMessage());
    }
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
