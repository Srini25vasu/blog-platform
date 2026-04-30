package com.platform.blog.service.shared.infrastructure;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import jakarta.persistence.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

/**
 * AuditingEntityListener: A specialized listener provided by Spring Data JPA.
 * When registered via @EntityListeners(AuditingEntityListener.class),
 * it automatically populates fields annotated with @CreatedDate, @CreatedBy,
 * @LastModifiedDate, and @LastModifiedBy
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractPersistenceEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @CreatedDate
  private Instant createdAt;

  @LastModifiedDate
  private Instant updatedAt;

  @Override
  public boolean equals(Object o) {
    if(this == o) return true;
    if(o == null || getClass() != o.getClass()) return false;
    AbstractPersistenceEntity that = (AbstractPersistenceEntity) o;
    return id != null && id.equals(that.id);
  }
  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
