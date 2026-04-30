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
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {

  private final BlogRepository blogRepository;
  private final BlogMapper blogMapper;

  @Override
  public BlogDto saveBlog(BlogDto blogDto) {
    log.info("Blog received: {}", blogDto);
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
  public BlogDto getBlogById(String id) {
    if (id == null) {
      throw BlogException.invalidInput("Blog id must not be null");
    }
    try {
      return blogRepository.findById(UUID.fromString(id))
          .map(blogMapper::toBlogDto)
          .orElseThrow(() -> BlogException.invalidInput("Blog not found with id: " + id));
    } catch (DataAccessException ex) {
      log.error("Database error while getting blog by id {}: {}", id, ex.getMessage(), ex);
      throw BlogException.getAllFailed(ex.getMessage());
    } catch (Exception ex) {
      log.error("Unexpected error while getting blog by id {}: {}", id, ex.getMessage(), ex);
      throw BlogException.getAllFailed(ex.getMessage());
    }
  }

  @Override
  public List<BlogDto> getAllBlogs() {
    try {
      List<Blog> blogs = blogRepository.findAll();
      return blogMapper.toBlogDtoList(blogs);
    } catch (DataAccessException ex) {
      log.error("Database error while getting all blogs: {}", ex.getMessage(), ex);
      throw BlogException.getAllFailed(ex.getMessage());
    } catch (Exception ex) {
      log.error("Unexpected error while getting all blogs: {}", ex.getMessage(), ex);
      throw BlogException.getAllFailed(ex.getMessage());
    }
  }

  @PostConstruct
  public void log() {
    log.info("BlogServiceImpl initialized");
  }
}
