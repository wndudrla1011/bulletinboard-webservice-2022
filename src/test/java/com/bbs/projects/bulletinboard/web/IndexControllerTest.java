package com.bbs.projects.bulletinboard.web;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IndexControllerTest {

    //필드 주입
    @Autowired
    private TestRestTemplate restTemplate;

    //index 페이지 호출 테스트
    @Test
    public void load_main() throws Exception{
        //when
        //루트 페이지에 Get 요청 결과를 문자열 객체로 받음
        String body = this.restTemplate.getForObject("/", String.class);

        //then
        //루트 페이지에 해당 문자열이 있는지 검증
        assertThat(body).contains("Welcome To Rootable's Free Board");
    }

}