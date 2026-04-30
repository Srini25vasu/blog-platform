# 📝 Blog Platform

A full-featured **Blog Platform** built with **Spring Boot**, designed to support a rich content ecosystem including blogs, posts, comments, reactions, followers, media assets, tags, categories, and post revisions.

---

## 🚀 Features

| Feature | Description |
|---|---|
| **Blogs** | Users can own and manage one or more blogs with custom slugs, descriptions, and settings |
| **Posts** | Rich content posts with versioning, full-text search (`tsvector`), metadata, and publishing workflow |
| **Post Revisions** | Full revision history per post — track every edit with a content snapshot, editor, and version number |
| **Comments** | Threaded (recursive) comments on posts, with moderation status support |
| **Reactions** | Emoji-based reactions on both posts and comments |
| **Followers** | Users can follow any blog and get updates |
| **Tags & Categories** | Posts can be tagged and grouped into hierarchical categories (supports sub-categories) |
| **Media Assets** | Upload and attach media (images, files) to posts via S3/CDN integration |
| **User Profiles** | Extended user profiles with bio, avatar, and social links (stored as JSONB) |

---

## 🏗️ Architecture

The platform follows a layered Spring Boot architecture:

```
Controller → Service (Interface + Impl) → Repository (Spring Data JPA) → PostgreSQL
```

- **Database**: PostgreSQL with a dedicated `portfolio` schema
- **ORM**: Hibernate / Spring Data JPA
- **Content storage**: JSONB columns for flexible post content and metadata
- **Search**: Native PostgreSQL `tsvector` full-text search on posts
- **Media**: S3-compatible object storage with CDN URL references

---

## 🗂️ Entity Relationship Diagram

The ERD below shows all core entities and their relationships. Open [`architecture_docs/blog_platform_erd.html`](architecture_docs/blog_platform_erd.html) in a browser to view the interactive rendered diagram.

```mermaid
erDiagram
  USER {
    uuid id PK
    string username
    string email
    string password_hash
    string role
    timestamp created_at
    timestamp updated_at
  }

  USER_PROFILE {
    uuid id PK
    uuid user_id FK
    string display_name
    text bio
    string avatar_url
    jsonb social_links
    timestamp updated_at
  }

  BLOG {
    uuid id PK
    uuid owner_id FK
    string slug
    string title
    text description
    string status
    jsonb settings
    timestamp created_at
  }

  POST {
    uuid id PK
    uuid blog_id FK
    uuid author_id FK
    string slug
    string title
    jsonb content
    jsonb metadata
    string status
    tsvector search_vector
    timestamp published_at
    timestamp created_at
    timestamp updated_at
  }

  POST_REVISION {
    uuid id PK
    uuid post_id FK
    uuid editor_id FK
    jsonb content_snapshot
    string change_summary
    int version_number
    timestamp created_at
  }

  TAG {
    uuid id PK
    string name
    string slug
    string color
  }

  POST_TAG {
    uuid post_id FK
    uuid tag_id FK
  }

  CATEGORY {
    uuid id PK
    uuid blog_id FK
    uuid parent_id FK
    string name
    string slug
    int sort_order
  }

  MEDIA_ASSET {
    uuid id PK
    uuid uploaded_by FK
    uuid post_id FK
    string s3_key
    string s3_bucket
    string cdn_url
    string mime_type
    bigint file_size_bytes
    jsonb metadata
    timestamp uploaded_at
  }

  COMMENT {
    uuid id PK
    uuid post_id FK
    uuid author_id FK
    uuid parent_id FK
    text body
    string status
    timestamp created_at
    timestamp updated_at
  }

  REACTION {
    uuid id PK
    uuid user_id FK
    uuid post_id FK
    uuid comment_id FK
    string emoji_code
    timestamp created_at
  }

  FOLLOW {
    uuid id PK
    uuid follower_id FK
    uuid blog_id FK
    timestamp followed_at
  }

  USER ||--|| USER_PROFILE : "has (composition)"
  USER ||--o{ BLOG : "owns"
  USER ||--o{ POST : "authors"
  USER ||--o{ COMMENT : "writes"
  USER ||--o{ REACTION : "gives"
  USER ||--o{ FOLLOW : "follows"
  USER ||--o{ MEDIA_ASSET : "uploads"
  BLOG ||--|{ POST : "contains (composition)"
  BLOG ||--o{ CATEGORY : "organises"
  BLOG ||--o{ FOLLOW : "followed by"
  POST ||--o{ POST_REVISION : "versioned by (aggregation)"
  POST ||--o{ COMMENT : "receives"
  POST ||--o{ REACTION : "receives"
  POST ||--o{ MEDIA_ASSET : "has (aggregation)"
  POST }o--o{ TAG : "tagged with"
  POST_TAG }o--|| POST : "links"
  POST_TAG }o--|| TAG : "links"
  COMMENT ||--o{ COMMENT : "replies to (recursive)"
  CATEGORY ||--o{ CATEGORY : "sub-category (recursive)"
  CATEGORY ||--o{ POST : "groups"
  USER ||--o{ POST_REVISION : "edits"
```

---

## ⚙️ Configuration

| Profile | File | Purpose |
|---|---|---|
| `dev` | `application-dev.properties` | Local development (PostgreSQL on localhost) |
| `int` | `application-int.properties` | Integration / staging environment |
| `prod` | `application-prod.properties` | Production environment |

Set the active profile via:
```
-Dspring.profiles.active=dev
```

All entities are stored under the **`portfolio`** schema inside the `blogdb` PostgreSQL database, configured via:
```properties
spring.jpa.properties.hibernate.default_schema=portfolio
```

---

## 🛠️ Tech Stack

- **Java 21** / **Spring Boot 3**
- **Spring Data JPA** + **Hibernate**
- **PostgreSQL** (with JSONB & tsvector support)
- **Lombok** (builder, getters/setters)
- **MapStruct** (DTO mapping)
- **OpenAPI / Swagger** (REST API spec at `rest_api/blog_api.yaml`)

---

## 📁 Project Structure

```
src/main/java/com/platform/blog/service/
├── blog/               # Blog aggregate (entity, controller, service, repo, dto)
├── config/             # Spring configuration classes
├── enums/              # Shared enumerations (e.g. BlogStatus)
├── exception/          # Global exception handling
├── interceptors/       # HTTP interceptors / filters
├── aspects/            # AOP aspects (e.g. logging)
└── shared/
    ├── domain/         # AbstractEntity (domain base)
    └── infrastructure/ # AbstractPersistenceEntity (JPA base)
```
