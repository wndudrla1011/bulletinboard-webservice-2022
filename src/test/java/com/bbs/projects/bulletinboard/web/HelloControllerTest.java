package com.bbs.projects.bulletinboard.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc; //Web Api 환경 제공

    @Test
    public void return_hello() throws Exception {
        String hello = "hello";

        //상태 코드와 메시지 바디 검증
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    public void return_helloDto() throws Exception{
        String name = "hello";
        int amount = 1000;

        //JSON 필드별 검증
        mvc.perform(get("/hello/dto")
                        .param("name", name) //요청 파라미터 설정
                        .param("amount", String.valueOf(amount))) //요청 파라미터 설정
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }

}