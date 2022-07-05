package com.bbs.projects.bulletinboard.web.dto;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class HelloResponseDtoTest {

    @Test
    public void test_lombok() throws Exception{
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }

}