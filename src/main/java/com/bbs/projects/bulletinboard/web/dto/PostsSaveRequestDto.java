package com.bbs.projects.bulletinboard.web.dto;

import com.bbs.projects.bulletinboard.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

    private String title;
    private String content;
    private String author;

    //객체 생성 시점에 초기화
    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    //각 속성 값에 컨트롤러->서비스로부터 받은 DTO(POST 요청 파라미터)를 매칭
    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
