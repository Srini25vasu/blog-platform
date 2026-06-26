package com.platform.blog.service.blog.impl;

import com.platform.blog.service.blog.Blog;
import com.platform.blog.service.blog.dto.BlogDto;
import com.platform.blog.service.blog.mapper.BlogMapper;
import com.platform.blog.service.blog.persistence.BlogRepository;
import com.platform.blog.service.blog.BlogStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {

  @Mock
  private BlogRepository blogRepository;

  @Mock
  private BlogMapper blogMapper;

  @InjectMocks
  private BlogServiceImpl blogService;

  @Test
  void testGetAllBlogs() {
    List<Blog> blogs = List.of(
          new Blog(
                "blug-slug",
                "Spring boot with GraphQL",
                null,
                "Reactive Programming",
                BlogStatus.PUBLISHED,
                List.of()
          ),
          new Blog());
    List<BlogDto> blogDtos = List.of(
          new BlogDto(
                UUID.randomUUID(),
                "blug-slug",
                "Spring boot with GraphQL",
                null,
                "React programmer",
                "Reactive Programming",
                BlogStatus.PUBLISHED
          )
          );
    when(blogRepository.findAll()).thenReturn(blogs);
    when(blogMapper.toBlogDtoList(blogs)).thenReturn(blogDtos);

    List<BlogDto> result = blogService.getAllBlogs();
    assertEquals(blogDtos, result);
  }
}
