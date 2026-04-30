package com.platform.blog.service.blog.mapper;

import com.platform.blog.service.blog.Blog;
import com.platform.blog.service.blog.dto.BlogDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BlogMapper {
  Blog toBlog(BlogDto blogDto);
  BlogDto toBlogDto(Blog blog);
  List<BlogDto> toBlogDtoList(List<Blog> blogs);
  List<Blog> toBlogList(List<BlogDto> blogDtos);
}
