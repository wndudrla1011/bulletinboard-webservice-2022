package com.bbs.projects.bulletinboard.web;

import com.bbs.projects.bulletinboard.domain.posts.Posts;
import com.bbs.projects.bulletinboard.domain.posts.PostsRepository;
import com.bbs.projects.bulletinboard.web.dto.PostsSaveRequestDto;
import com.bbs.projects.bulletinboard.web.dto.PostsUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    //현재 실행 중인 포트 넘버
    @LocalServerPort
    private int port;

    //필드 주입
    @Autowired
    private TestRestTemplate restTemplate;

    //필드 주입
    @Autowired
    private PostsRepository postsRepository;

    //필드 주입
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc; //Web Api 환경 제공

    //매번 테스트 시작 전에 MockMvc 인스턴스 생성
    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    //테스트 메소드 종료마다 DB 정리
    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    //게시글 등록 테스트
    @Test
    @WithMockUser(roles = "USER") //임의 인증된 사용자 추가
    public void register_posts() throws Exception{
        //given
        String title = "title";
        String content = "content";
        //요청 파라미터들 셋팅
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        //컨트롤러 테스트를 위한 url 변수 선언
        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        //API 요청 수행
        mvc.perform(post(url) //해당 URL로 POST 요청
                        .contentType(MediaType.APPLICATION_JSON_UTF8) //객체 전송을 위해 JSON 타입 지정
                        .content(new ObjectMapper().writeValueAsString(requestDto))) //각 속성값들을 문자열로 바디에 삽입
                .andExpect(status().isOk());

        //then
        //DB 전체 필드 조회를 통한 검증
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    //게시글 갱신 테스트
    @Test
    @WithMockUser(roles = "USER") //임의 인증된 사용자 추가
    public void update_posts() throws Exception{
        //given
        //각 속성 값 임의 지정
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2"; //테스트용 title
        String expectedContent = "content2"; //테스트용 content

        //갱신용 DTO에 테스트용 title과 content 저장
        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        //컨트롤러 테스트를 위한 url 변수 선언
        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        //갱신용 DTO를 통해 requestEntity를 얻음
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        //API 요청 수행
        mvc.perform(put(url) //해당 URL로 PUT 요청
                        .contentType(MediaType.APPLICATION_JSON_UTF8) //객체 전송을 위해 JSON 타입 지정
                        .content(new ObjectMapper().writeValueAsString(requestDto))) //각 속성값들을 문자열로 바디에 삽입
                .andExpect(status().isOk());

        //then
        //DB 전체 필드 조회를 통한 검증
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

    }

}