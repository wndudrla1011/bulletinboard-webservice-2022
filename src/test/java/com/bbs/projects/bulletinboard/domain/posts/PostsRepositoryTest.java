package com.bbs.projects.bulletinboard.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    //필드 주입
    @Autowired
    PostsRepository postsRepository;

    //테스트 메소드 종료마다 DB 정리
    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    //DB 저장 테스트
    @Test
    public void save_posts() throws Exception{
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        //빌더를 통해 DB에 각 속성 저장
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("pink@gmail.com")
                .build());

        //when
        //전체 속성 조회
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0); //1번째 레코드 추출
        //검증
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    //생성 시간, 수정 시간 테스트
    @Test
    public void register_baseTimeEntity() throws Exception{
        //given
        //지정된 시간 저장
        LocalDateTime now = LocalDateTime.of(2022, 7, 6, 0, 0, 0);
        //title, content, author 값을 DB에 저장
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        //when
        //전체 속성 값 조회
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0); //1번째 레코드 추출

        //생성 시간과 수정 시간 출력 테스트
        System.out.println(">>>>>>>>>> createDate = " + posts.getCreatedDate() +
                ", modifiedDate = " + posts.getModifiedDate());

        //검증
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);

    }

}