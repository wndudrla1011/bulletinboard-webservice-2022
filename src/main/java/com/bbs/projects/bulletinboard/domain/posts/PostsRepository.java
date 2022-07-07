package com.bbs.projects.bulletinboard.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

//DAO + 자동 Bean 등록
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
