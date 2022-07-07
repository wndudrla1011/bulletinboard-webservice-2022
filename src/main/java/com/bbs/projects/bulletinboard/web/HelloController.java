package com.bbs.projects.bulletinboard.web;

import com.bbs.projects.bulletinboard.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        //HTTP 메시지 바디에 직접 데이터를 write
        return "hello";
    }

    @GetMapping("/hello/dto")
    //URL 상에서 해당 파라미터를 찾아서 DTO에 넣은 DTO 객체 반환
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount) {

        return new HelloResponseDto(name, amount);

    }
}
