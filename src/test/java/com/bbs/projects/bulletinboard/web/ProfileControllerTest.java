package com.bbs.projects.bulletinboard.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProfileControllerTest {

    @LocalServerPort
    private int port;

    //필드 주입
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void call_profile_without_certification() throws Exception{

        String expected = "default";
        //http 헤더 접근을 위해 ResponseEntity를 받음
        ResponseEntity<String> response = restTemplate.getForEntity("/profile", String.class);
        //HTTP 헤더 상태 검증
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //HTTP 바디에 "default"가 있는지 검증
        assertThat(response.getBody()).isEqualTo(expected);

    }

}