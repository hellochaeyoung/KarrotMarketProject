package com.project.karrot.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.karrot.controller.auth.WithMockCustomMember;
import com.project.karrot.src.member.dto.MemberAndImageResponseDto;
import com.project.karrot.src.member.dto.MemberLoginRequestDto;
import com.project.karrot.src.member.dto.MemberResponseDto;
import com.project.karrot.src.product.dto.ProductResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class MyPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void init() throws Exception {

        //given
        MemberLoginRequestDto loginRequestDto = MemberLoginRequestDto.builder()
                                                    .email("cyahn@gmail.com")
                                                    .password("cyahn").build();

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/login/auth")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequestDto))
                            .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
                            .andExpect(status().isOk());


    }

    @Test
    @WithMockCustomMember
    void 프로필_조회() throws Exception {

        //when
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/mypage/profile"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        MemberAndImageResponseDto responseDto = objectMapper.readValue(result, new TypeReference<>() {});

        //then
        assertThat(responseDto.getNickName()).isEqualTo("hellochaeyoung");

    }

    @Test
    @WithMockCustomMember
    void 프로필_닉네임_수정() throws Exception {

        String newNickName = "hellocy";

        //when
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/mypage/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newNickName))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        MemberResponseDto responseDto = objectMapper.readValue(result, new TypeReference<>() {});

        assertThat(responseDto.getNickName()).isEqualTo("hellocy");
    }

    @Test
    @WithMockCustomMember
    void 등록상품_목록_조회() throws Exception {

        //when
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/mypage/myProducts/status/" + "SALE"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<ProductResponseDto> resultList = objectMapper.readValue(result, new TypeReference<>() {});

        //then
        assertThat(resultList.size()).isGreaterThan(0);

    }

}
