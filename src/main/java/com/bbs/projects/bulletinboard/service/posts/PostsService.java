package com.bbs.projects.bulletinboard.service.posts;

import com.bbs.projects.bulletinboard.domain.posts.Posts;
import com.bbs.projects.bulletinboard.domain.posts.PostsRepository;
import com.bbs.projects.bulletinboard.web.dto.PostsResponseDto;
import com.bbs.projects.bulletinboard.web.dto.PostsSaveRequestDto;
import com.bbs.projects.bulletinboard.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    //생성 시점에 PostsRepository 의존성을 받음
    private final PostsRepository postsRepository;

    //컨트롤러에서 전달 받은 DTO의 메소드 호출 결과를 저장하고, 저장 ID를 반환
    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    //update 기능
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        //findById로 대상 게시글 조회, 존재하지 않을 경우 예외 발생시킴
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        //게시글의 title, content 갱신
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id; //PK 반환
    }

    //조회 기능
    public PostsResponseDto findById(Long id) {
        //findById로 대상 게시글 조회, 존재하지 않을 경우 예외 발생시킴
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        //조회용 DTO에 조회한 객체를 담은 DTO 객체 반환
        return new PostsResponseDto(entity);
    }

}
