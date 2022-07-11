package com.bbs.projects.bulletinboard.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    //guest와 user라는 권한 목록 생성
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    //각 권한이 가질 필드 선언 + 생성자 주입
    private final String key;
    private final String title;

}
