package com.project.karrot.src.member;

import com.project.karrot.common.Api.Response;
import com.project.karrot.constants.SessionConstants;
import com.project.karrot.src.location.dto.LocationResponseDto;
import com.project.karrot.src.member.dto.MemberRequestDto;
import com.project.karrot.src.member.dto.MemberResponseDto;
import com.project.karrot.src.product.dto.ProductResponseDto;
import com.project.karrot.src.ProductStatus;
import com.project.karrot.src.location.LocationService;
import com.project.karrot.src.product.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final LocationService locationService;
    private final ProductService productService;

    @ApiOperation(value = "유저 등록 상품 목록 조회", notes = "상품 등록자의 모든 등록된 상품을 조회한다.")
    @PostMapping("/{memberId}/products/{status}")
    public List<ProductResponseDto> allByStatus(@PathVariable Long memberId, @PathVariable String status) {

        List<ProductResponseDto> list = new ArrayList<>();

        if(status.equals("ALL")) {
            list = productService.findByMemberId(memberId);
        }else if(status.equals("SALE")) {
            list = productService.findByMemberAndStatus(memberId, ProductStatus.SALE);
        }else {
            /* 수정 필요
            for(Deal deal : memberResponseDto.getDeals()) {
                list.add(deal.getProduct());
            }

             */
        }

        return list;
    }

}

