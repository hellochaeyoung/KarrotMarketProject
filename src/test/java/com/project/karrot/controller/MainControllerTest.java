package com.project.karrot.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.karrot.controller.auth.WithMockCustomMember;
import com.project.karrot.src.category.dto.CategoryAndLocationRequestDto;
import com.project.karrot.src.location.dto.LocationResponseDto;
import com.project.karrot.src.product.dto.ProductAndCategoryRes;
import lombok.extern.slf4j.Slf4j;
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
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockCustomMember
    void 메인화면_조회() throws Exception {

        //given
        CategoryAndLocationRequestDto requestDto =
                CategoryAndLocationRequestDto.builder()
                .categoryId(7L)
                .locationId(2L)
                .build();

        //when
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/main/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestDto))
        .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ProductAndCategoryRes response = objectMapper.readValue(result, new TypeReference<>() {});

        //then
        assertThat(response.getCategoryList().size()).isGreaterThan(0);
        assertThat(response.getProductList().size()).isGreaterThan(0);

    }

    @Test
    @WithMockCustomMember
    void 지역_검색_조회() throws Exception {
        //given
        String locationName = "경기도 부천시";

        //when
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/main/location")
                                    .contentType(MediaType.TEXT_PLAIN)
                                    .content(locationName))
                                    .andExpect(status().isOk())
                                    .andReturn().getResponse().getContentAsString();

        List<LocationResponseDto> resultList = objectMapper.readValue(result, new TypeReference<>(){});

        //then
        assertThat(resultList.size()).isGreaterThan(0);
    }

    @Test
    @WithMockCustomMember
    void 지역_검색_조회_실패() throws Exception {
        //given
        String locationName = "로스앤젤레스";

        //when
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/main/location")
                                        .contentType(MediaType.TEXT_PLAIN)
                                        .content(locationName))
                                .andExpect(status().isOk())
                                .andReturn().getResponse().getContentAsString();

        List<LocationResponseDto> resultList = objectMapper.readValue(result, new TypeReference<List<LocationResponseDto>>() {});

        //then
        assertThat(resultList.size()).isEqualTo(0);
    }
}
