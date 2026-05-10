package com.platform.blog.service.blog;

import com.platform.blog.service.shared.infrastructure.JsonNodeType;
import com.platform.blog.service.shared.infrastructure.AbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import tools.jackson.databind.JsonNode;

import java.util.List;

@Entity(name = "blog")
@Getter
@Setter
@NoArgsConstructor
public class Blog extends AbstractPersistenceEntity {

  @Column(name = "slug", nullable = false, unique = true)
  private String slug;

  @Column(name = "title", nullable = false)
  private String title;

  /**
   * Found the root cause. You're using Spring Boot 4.0.6 which bundles Jackson 3.x —
   * that's why the import is tools.jackson.databind.JsonNode instead of com.fasterxml.jackson.databind.JsonNode.
   * Hibernate does not know how to map tools.jackson.databind.JsonNode to a JDBC type out of the box.
   */
  @Column(columnDefinition = "jsonb")
  @ColumnTransformer(write = "?::jsonb")
  @JavaType(JsonNodeType.class)
  private JsonNode content;

  @Column(name = "description")
  private String description;

  @Enumerated(EnumType.STRING)
  /**
   * Added @JdbcTypeCode(SqlTypes.VARCHAR) on the status field. This explicitly tells Hibernate
   * to treat the enum column as a plain VARCHAR, so it stores "DRAFT", "PUBLISHED", "ARCHIVED"
   * as strings — the same behavior as Hibernate 5.x did by default.
   */
  @JdbcTypeCode(SqlTypes.VARCHAR)
  @Column(name = "status")
  private BlogStatus status;

  @OneToMany(mappedBy = "blog", fetch = FetchType.LAZY)
  @BatchSize(size = 20)
  private List<Post> posts;
}
