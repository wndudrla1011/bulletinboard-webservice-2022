package com.bbs.projects.bulletinboard.web;

import com.bbs.projects.bulletinboard.service.posts.PostsService;
import com.bbs.projects.bulletinboard.web.dto.PostsResponseDto;
import com.bbs.projects.bulletinboard.web.dto.PostsSaveRequestDto;
import com.bbs.projects.bulletinboard.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    //생성 시점에 PostsService 의존성을 받음
    private final PostsService postsService;

    //등록 기능용 컨트롤러
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        //POST 요청의 각 파라미터를 DTO의 각 필드에 파싱 후 DTO를 저장
        return postsService.save(requestDto);
    }

    //update 기능용 컨트롤러 + 갱신 대상 게시글의 id를 통한 동적 URL 생성
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        //게시글 갱신 후 서비스에서 해당 id를 반환함
        return postsService.update(id, requestDto);
    }

    //조회 기능용 컨트롤러 + 갱신 대상 게시글의 id를 통한 동적 URL 생성
    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        //대상 게시글 id로 서비스의 findById를 호출
        return postsService.findById(id);
    }

    //js의 삭제 요청을 받아 서비스의 삭제 기능을 호출
    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }
}
