package com.project.karrot.service;

import com.project.karrot.src.ProductStatus;
import com.project.karrot.src.member.MemberService;
import com.project.karrot.src.member.dto.MemberResponseDto;
import com.project.karrot.src.product.ProductService;
import com.project.karrot.src.product.dto.ProductAndImageResponseDto;
import com.project.karrot.src.product.dto.ProductRequestDto;
import com.project.karrot.src.product.dto.ProductResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class ProductServiceIntegrationTest {

    @Autowired
    MemberService memberService;

    @Autowired
    ProductService productService;

    private Long memberId = 9L;
    private Long productId;
    private ProductRequestDto productRequestDto;
    private ProductResponseDto productResponseDto;

    @BeforeEach
    void init() {

        //상품 등록
        productRequestDto = ProductRequestDto.builder()
                .productName("그램 노트북")
                .contents("그램 15인치 팝니다.")
                .categoryName("디지털기기")
                .price(600000)
                .memberId(memberId)
                .build();

        ProductAndImageResponseDto p = productService.register(productRequestDto);
        productId = p.getProductResponseDto().getProductId();

    }

    @Test
    void 상품등록_이미지제외() {
        //given
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .productName("에어팟 2세대")
                .contents("중고 에어팟 2세대 팝니다.")
                .categoryName("디지털기기")
                .price(120000)
                .memberId(memberId)
                .build();

        //when
        ProductAndImageResponseDto p = productService.register(productRequestDto);

        //then
        MemberResponseDto findMember = memberService.find(p.getProductResponseDto().getMemberId());
        List<ProductResponseDto> result = productService.findByMemberId(findMember.getId());

        assertEquals(result.get(0).getMemberId(), findMember.getId());
    }

    @Test
    void 상품_등록상태_수정() {

        productRequestDto.setProductId(productId);
        productService.updateStatus(productRequestDto, ProductStatus.COMPLETE);

        List<ProductResponseDto> resultList = productService.findByMemberAndStatus(memberId, ProductStatus.COMPLETE);

        assertThat(resultList.size()).isGreaterThan(0);
    }

    @Test
    void 상품조회_id() {
        ProductResponseDto result = productService.findById(productId);

        assertEquals(result.getProductName(), "그램 노트북");
    }

    @Test
    void 상품조회_지역() {
        List<ProductResponseDto> resultList = productService.findByLocation(2L);
        assertThat(resultList.size()).isGreaterThan(0);
    }

    @Test
    void 상품조회_지역_카테고리() {
        List<ProductResponseDto> resultList = productService.findByLocationAndCategory(2L, 1L);
        assertThat(resultList.size()).isGreaterThan(0);
    }

    @Test
    void 상품조회_회원() {
        List<ProductResponseDto> resultList = productService.findByMemberId(memberId);
        assertThat(resultList.size()).isGreaterThan(0);
    }

    @Test
    void 상품조회_회원_상품등록상태() {
        List<ProductResponseDto> resultList = productService.findByMemberAndStatus(memberId, ProductStatus.SALE);
        assertThat(resultList.size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void 상품_삭제() {
        productService.remove(productId);

        assertThrows(NoSuchElementException.class, () -> productService.findById(productId));
    }

}
