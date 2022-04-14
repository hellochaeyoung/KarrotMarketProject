package com.project.karrot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.karrot.src.location.LocationService;
import com.project.karrot.src.member.MemberService;
import com.project.karrot.src.member.dto.MemberRequestDto;
import com.project.karrot.src.views.HomeController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class HomeControllerTest {

    @InjectMocks
    private HomeController homeController;

    @Autowired
    private LocationService locationService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
     void init() throws Exception {

        //given
        MemberRequestDto memberRequestDto
                = MemberRequestDto.builder()
                .email("bbb@gmail.com")
                .password("bbb")
                .name("banana")
                .nickName("banana")
                .locationName("서울특별시")
                .phoneNumber("010-1111-3333")
                .build();


        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/home/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequestDto))
        );

        //then
        final MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        final String token = mvcResult.getResponse().getContentAsString();

        log.info(token);
        assertThat(token).isNotNull();

    }

    @Test
     void 홈_화면_조회() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
     void 회원가입_성공() throws Exception {

        //given
        MemberRequestDto memberRequestDto
                = MemberRequestDto.builder()
                    .email("ccc@gmail.com")
                    .password("ccc")
                    .name("candy")
                    .nickName("candy")
                    .locationName("부천시 상동")
                    .phoneNumber("010-2222-3333")
                    .build();


        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/home/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberRequestDto))
        );

        //then
        final MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        final String token = mvcResult.getResponse().getContentAsString();

        log.info(token);
        assertThat(token).isNotNull();
    }

    @Test
    void 회원가입_실패_이메일중복() throws Exception {

        //given
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .email("bbb@gmail.com")
                .password("bbbbb")
                .name("banana")
                .nickName("bbb")
                .locationName("서울특별시")
                .phoneNumber("010-111-3333")
                .build();

        //when
        final ResultActions resultActions = mockMvc
                .perform(
                MockMvcRequestBuilders.post("/home/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberRequestDto)))

                .andExpect(status().isBadRequest());

        //then
        int status = resultActions.andReturn().getResponse().getStatus();
        String message = resultActions.andReturn().getResponse().getContentAsString();

        log.info("message : {}", message);

        assertThat(status).isEqualTo(400);

    }

    @Test
    void 회원가입_실패_닉네임중복() throws Exception {

        //given
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .email("ccc@gmail.com")
                .password("ccccc")
                .name("candy")
                .nickName("banana")
                .locationName("서울특별시")
                .phoneNumber("010-1112-3333")
                .build();

        //when
        int result = mockMvc.perform(MockMvcRequestBuilders.post("/home/signUp")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(memberRequestDto))).andExpect(status().isBadRequest()).andReturn().getResponse().getStatus();

        //then
        assertThat(result).isEqualTo(400);
    }
}
