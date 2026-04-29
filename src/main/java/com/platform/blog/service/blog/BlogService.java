package com.platform.blog.service.blog;

import com.platform.blog.service.blog.dto.BlogDto;

import java.util.List;

public interface BlogService {
  BlogDto saveBlog(BlogDto blog);
  BlogDto updateBlog(BlogDto blog);
  BlogDto deleteBlog(BlogDto blog);
  BlogDto getBlogById(Long id);
  List<BlogDto> getAllBlogs();
}
