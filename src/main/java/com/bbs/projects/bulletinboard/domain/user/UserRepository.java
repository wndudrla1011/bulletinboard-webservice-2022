package com.bbs.projects.bulletinboard.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //email로 신규 가입자인지 조회
    Optional<User> findByEmail(String email);

}
