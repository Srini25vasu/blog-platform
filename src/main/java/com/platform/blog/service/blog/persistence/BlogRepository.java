package com.platform.blog.service.blog.persistence;

import com.platform.blog.service.blog.Blog;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlogRepository extends JpaRepository<Blog, UUID> {

  /**
   * Fetches a single Blog with its posts in one query using a JOIN FETCH,
   * avoiding the N+1 problem when posts are needed alongside the blog.
   */
  @Query("SELECT b FROM blog b LEFT JOIN FETCH b.posts WHERE b.id = :id")
  Optional<Blog> findByIdWithPosts(UUID id);

  /**
   * Fetches all Blogs with their posts in one query.
   * Uses DISTINCT to avoid duplicate Blog rows caused by the JOIN.
   */
  @Query("SELECT DISTINCT b FROM blog b LEFT JOIN FETCH b.posts")
  List<Blog> findAllWithPosts();

  /**
   * Uses @EntityGraph to fetch posts eagerly only for this query,
   * keeping the default findById lazy. Useful for Spring Data method naming
   * conventions combined with eager loading.
   */
  @EntityGraph(attributePaths = "posts")
  @Query("SELECT b FROM blog b WHERE b.slug = :slug")
  Optional<Blog> findBySlugWithPosts(String slug);
}
