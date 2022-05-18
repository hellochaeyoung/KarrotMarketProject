package com.project.karrot.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.karrot.controller.auth.WithMockCustomMember;
import com.project.karrot.src.interest.dto.InterestedRequestDto;
import com.project.karrot.src.product.dto.ProductListResponseDto;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @WithMockCustomMember
    void 상품_상세_조회() throws Exception {

        //given
        Long productId = 1L;
        Long memberId = 9L;

        //when
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/products/" + productId + "?registerMemberId=" + memberId))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ProductListResponseDto responseDto = objectMapper.readValue(result, new TypeReference<>() {});
        log.info(responseDto.getSelectProduct().getProductName());
        log.info(String.valueOf(responseDto.getOtherProductList().size()));

        //then
        assertThat(responseDto.getSelectProduct().getProductId()).isEqualTo(productId);
        assertThat(responseDto.getOtherProductList().size()).isGreaterThan(0);

    }

    @Test
    @WithMockCustomMember
    void 좋아요_추가() throws Exception {

        //given
        Long productId = 1L;
        Long memberId = 9L;

        InterestedRequestDto requestDto = InterestedRequestDto.builder()
                .productId(productId)
                .memberId(memberId)
                .like(true)
                .build();

        //when
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        int count = objectMapper.readValue(result, new TypeReference<>() {});

        //then
        assertThat(count).isEqualTo(2);
    }

    @Test
    @WithMockCustomMember
    void 좋아요_취소() throws Exception {

        //given
        Long productId = 1L;
        Long memberId = 9L;

        InterestedRequestDto requestDto = InterestedRequestDto.builder()
                .productId(productId)
                .memberId(memberId)
                .like(false)
                .build();

        //when
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/products/" + productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        int count = objectMapper.readValue(result, new TypeReference<>() {});

        //then
        assertThat(count).isEqualTo(0);
    }



}
