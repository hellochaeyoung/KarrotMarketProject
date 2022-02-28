package com.project.karrot.src.views;

import com.project.karrot.constants.SessionConstants;
import com.project.karrot.src.category.CategoryService;
import com.project.karrot.src.category.dto.CategoryResponseDto;
import com.project.karrot.src.deal.DealService;
import com.project.karrot.src.member.MemberService;
import com.project.karrot.src.member.dto.MemberRequestDto;
import com.project.karrot.src.member.dto.MemberResponseDto;
import com.project.karrot.src.product.ProductService;
import com.project.karrot.src.ProductStatus;
import com.project.karrot.src.category.Category;
import com.project.karrot.src.deal.Deal;
import com.project.karrot.src.interest.InterestedProduct;
import com.project.karrot.src.product.dto.ProductAndCategoryRes;
import com.project.karrot.src.product.dto.ProductRequestDto;
import com.project.karrot.src.product.dto.ProductResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{memberId}/mypage")
public class MyPageController {

    private final MemberService memberService;
    private final ProductService productService;
    private final DealService dealService;
    private final CategoryService categoryService;

    @ApiOperation(value = "마이페이지 - 프로필 조회", notes = "프로필을 조회한다.")
    @GetMapping("/profile")
    public String profile(@PathVariable Long memberId) {

        MemberResponseDto memberResponseDto = memberService.find(memberId);

        return memberResponseDto.getNickName();

    }

    @ApiOperation(value = "마이페이지 - 프로필 수정", notes = "프로필을 수정한다.")
    @PutMapping("/profile")
    public MemberResponseDto change(@PathVariable Long memberId, @RequestBody String nickName) {
        return memberService.update(memberId, nickName);
    }

    @ApiOperation(value = "마이페이지 - 등록 상품 목록 조회", notes = "내가 등록한 상품들의 상품 상태별 목록을 조회한다.")
    @GetMapping("/myProducts/{status}")
    public List<ProductResponseDto> getProductList(@PathVariable Long memberId, @PathVariable String status) {

        List<ProductResponseDto> list = new ArrayList<>();

        MemberResponseDto member = memberService.find(memberId);

        if(status.equals("SALE")) { // 판매중
            list = productService.findByMemberAndStatus(memberId, ProductStatus.SALE);
        }else if(status.equals("COMPLETE")){ // 거래완료
            for(Deal deal : member.getDeals()) {
                ProductResponseDto productResponseDto = new ProductResponseDto(deal.getProduct());
                list.add(productResponseDto);
                System.out.println(productResponseDto.getProductName());
            }
        }

        return list;
    }

    @ApiOperation(value = "마이페이지 - 등록 상품 상태 수정", notes = "내가 등록한 상품의 상품 상태를 변경한다.")
    @PostMapping("/myProducts")
    public List<ProductResponseDto> updateStatus(@PathVariable Long memberId, @RequestBody String status) {

        List<ProductResponseDto> list = new ArrayList<>();

        MemberResponseDto member = memberService.find(memberId); // 추후 수정 필요

        // 수정 필요
        //setNewStatus(loginMember, product, updateStatus);

        if(status.equals("RESERVATION")) {
            list = productService.findByMemberAndStatus(member.getId(), ProductStatus.SALE);
        }else {
            for(Deal d : member.getDeals()) {
                ProductResponseDto productResponseDto = new ProductResponseDto(d.getProduct());
                list.add(productResponseDto);
            }
        }

        return list;

    }

    @ApiOperation(value = "마이페이지 - 관심 상품 목록 조회", notes = "관심 등록한 상품 목록을 상품 상태별로 조회한다.")
    @GetMapping("/myInterests/{status}")
    public List<InterestedProduct> getInterestedList(@PathVariable Long memberId, @PathVariable String status) {

        MemberResponseDto member = memberService.find(memberId);

        ArrayList<InterestedProduct> list = new ArrayList<>();
        if(status.equals("SALE")) {
            for(InterestedProduct p : member.getInterestedProducts()) {
                if(p.getProduct().getProductStatus().equals(ProductStatus.SALE)) { //////////////
                    list.add(p);
                }
            }
        }else {
            for(InterestedProduct p : member.getInterestedProducts()) {
                if(p.getProduct().getProductStatus().equals(ProductStatus.COMPLETE)) { ///////////
                    list.add(p);
                }
            }
        }

        return list;

    }

    @ApiOperation(value = "마이페이지 - 상품 정보 조회", notes = "등록 상품의 정보를 조회한다.")
    @GetMapping("/myProducts/{productId}")
    public ProductAndCategoryRes findUpdateProduct(@PathVariable Long productId) {

        List<CategoryResponseDto> categories = categoryService.findAll();
        ProductResponseDto product = productService.findById(productId);

        return new ProductAndCategoryRes(categories, product);
    }

    @ApiOperation(value = "마이페이지 - 등록 상품 수정", notes = "등록한 상품의 정보를 수정한다.")
    @PutMapping("/myProducts/{productId}")
    public ProductResponseDto update(@PathVariable Long productId, @RequestBody ProductRequestDto productReq) {
        return productService.update(productReq);
    }

    public void setNewStatus(MemberResponseDto member,ProductRequestDto product, String status) {

        if(status.equals("DELETE")) {
            dealService.findByProduct(product).ifPresent(deal -> {
                deal.setProduct(null);
                dealService.remove(deal);
            });
            productService.remove(product);
            return;
        }

        // 거래완료 -> 예약중, 판매중으로 변경 시 거래 내역 삭제
        if(product.getProductStatus().equals(ProductStatus.COMPLETE)) {
            dealService.findByProduct(product).ifPresent(deal -> {
                deal.setProduct(null);
                dealService.remove(deal);
            });

        }

        if(status.equals("RESERVATION")) {
            product.setProductStatus(ProductStatus.RESERVATION);
            productService.register(product); // 업데이트

        }else if(status.equals("SALE")) {
            product.setProductStatus(ProductStatus.SALE);
            productService.register(product); // 업데이트

        }else {
            product.setProductStatus(ProductStatus.COMPLETE);
            //Product result = productService.register(product, loginMember);

            /* -- 수정 필요!

            Deal deal = new Deal();
            deal.setMember(loginMember);
            deal.setProduct(result);
             */

            //dealService.register(deal);

        }
    }
}
