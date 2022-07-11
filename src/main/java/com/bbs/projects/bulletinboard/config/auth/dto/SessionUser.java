package com.bbs.projects.bulletinboard.config.auth.dto;

import com.bbs.projects.bulletinboard.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

//세션에 사용자 정보를 저장하기 위한 DTO + 직렬화 기능
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
