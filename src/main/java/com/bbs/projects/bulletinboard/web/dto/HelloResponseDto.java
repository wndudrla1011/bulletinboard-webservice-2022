package com.bbs.projects.bulletinboard.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HelloResponseDto {

    //불변 필드+생성자 주입
    private final String name;
    private final int amount;

}
