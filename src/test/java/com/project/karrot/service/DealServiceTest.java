package com.project.karrot.service;

import com.project.karrot.src.deal.DealService;
import com.project.karrot.src.deal.dto.DealRequestDto;
import com.project.karrot.src.deal.dto.DealResponseDto;
import com.project.karrot.src.product.ProductService;
import com.project.karrot.src.product.dto.ProductResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class DealServiceTest {

    @Autowired
    ProductService productService;
    @Autowired
    DealService dealService;

    private DealRequestDto dealRequestDto;
    private DealResponseDto dealResponseDto;

    @BeforeEach
    void init() {
        dealRequestDto = DealRequestDto.builder().memberId(9L).productId(3L).build();
        dealResponseDto = dealService.register(dealRequestDto);
    }

    @Test
    void 거래완료() {
        assertEquals(dealRequestDto.getProductId(), dealResponseDto.getProductResponseDto().getProductId());
    }
    @Test
    public void 거래완료_회원조회() {

        List<DealResponseDto> resultList = dealService.findByMember(dealRequestDto.getMemberId());

        assertThat(resultList.size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void 거래완료_상품조회() {

        DealResponseDto dealResponseDto = dealService.findByProduct(dealRequestDto.getProductId());

        ProductResponseDto productResponseDto = productService.findById(dealRequestDto.getProductId());

        assertEquals(dealResponseDto.getProductResponseDto().getProductName(), productResponseDto.getProductName());
    }


}
