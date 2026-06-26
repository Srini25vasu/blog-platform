package com.platform.blog.service.blog.controllers;

import com.platform.blog.service.blog.BlogService;
import com.platform.blog.service.blog.BlogStatus;
import com.platform.blog.service.blog.dto.BlogDto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.node.JsonNodeFactory;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BlogController.class)
class BlogControllerTest {

  @Autowired
  MockMvc mockMvc;

  // Required: @WebMvcTest only loads the web layer.
  // BlogController depends on BlogService, which must be mocked
  // so the Spring context can be created.
  @MockitoBean
  private BlogService blogService;

  @Test
  void testGetAllBlogs() throws Exception {
    when(blogService.getAllBlogs()).thenReturn(getBlogs());
    mockMvc.perform(get("/api/v1/blogs"))
          .andExpect(status().isOk())
          .andExpect(MockMvcResultMatchers.content().json(getBlogs().toString()));

  }
  private List<BlogDto> getBlogs() {
    return List.of(
          BlogDto.builder()
                .id(UUID.randomUUID())
                .slug("slug1")
                .title("title1")
                .content(JsonNodeFactory.instance.objectNode())
                .description("description1")
                .status(BlogStatus.DRAFT)
                .build(),
          BlogDto.builder()
                .id(UUID.randomUUID())
                .slug("slug2")
                .title("title2")
                .content(JsonNodeFactory.instance.objectNode())
                .description("description2")
                .status(BlogStatus.DRAFT)
                .build(),
          BlogDto.builder()
                .id(UUID.randomUUID())
                .slug("slug3")
                .title("title3")
                .content(JsonNodeFactory.instance.objectNode())
                .description("description3")
                .status(BlogStatus.DRAFT)
                .build()
    );
  }
}
