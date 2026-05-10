package com.platform.blog.service.blog;

import com.platform.blog.service.shared.infrastructure.AbstractPersistenceEntity;
import com.platform.blog.service.shared.infrastructure.JsonNodeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.JavaType;
import tools.jackson.databind.JsonNode;

import java.time.Instant;

@Entity(name = "post")
@Getter
@Setter
@NoArgsConstructor
public class Post extends AbstractPersistenceEntity {
  @Column(name = "title", nullable = false)
  private String title;

  @Column(columnDefinition = "jsonb")
  @ColumnTransformer(write = "?::jsonb")
  @JavaType(JsonNodeType.class)
  private JsonNode content;

  @Column(columnDefinition = "jsonb")
  @ColumnTransformer(write = "?::jsonb")
  @JavaType(JsonNodeType.class)
  private JsonNode metadata;

  @Column(name = "slug", nullable = false)
  private String slug;

  @Column(name = "description", nullable = false)
  private String description;

  @ManyToOne
  private Blog blog;
  private Instant publishedAt;
}
