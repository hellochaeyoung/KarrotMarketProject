package com.project.karrot.service;

import com.project.karrot.src.interest.InterestedService;
import com.project.karrot.src.interest.dto.InterestedRequestDto;
import com.project.karrot.src.interest.dto.InterestedResponseDto;
import com.project.karrot.src.member.MemberService;
import com.project.karrot.src.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
@Transactional
class InterestedServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    ProductService productService;

    @Autowired
    InterestedService interestedService;

    private Long memberId = 9L;
    private Long productId = 3L;
    private InterestedRequestDto interestedRequestDto;

    @BeforeEach
    void init() {
        interestedRequestDto = InterestedRequestDto.builder()
                .memberId(memberId)
                .productId(productId)
                .build();

        interestedRequestDto.setLike(true);
        interestedService.add(interestedRequestDto);

    }

    @Test
    public void 관심상품_등록() {

        //given
        int like = productService.findById(productId).getLikeCount();
        interestedRequestDto.setLike(true);

        //when
        InterestedResponseDto interestedResponseDto = interestedService.add(interestedRequestDto);

        //then
        log.info("before like : {}, after like : {}", like, interestedResponseDto.getProductResponseDto().getLikeCount());
        assertEquals(interestedResponseDto.getProductResponseDto().getLikeCount(), like+1);
    }

    @Test
    void 관심상품_해제() {

        //given
        int like = productService.findById(productId).getLikeCount();
        interestedRequestDto.setLike(false);

        //when
        InterestedResponseDto interestedResponseDto = interestedService.add(interestedRequestDto);

        //then
        log.info("before like : {}, after like : {}", like, interestedResponseDto.getProductResponseDto().getLikeCount());
        assertEquals(interestedResponseDto.getProductResponseDto().getLikeCount(), like-1);
    }

    @Test
    void 관심상품_조회() {

        List<InterestedResponseDto> resultList =
                interestedService.findInterestedByMemberIdAndProductStatus(memberId);

        Assertions.assertThat(resultList.size()).isGreaterThan(0);

    }
}
