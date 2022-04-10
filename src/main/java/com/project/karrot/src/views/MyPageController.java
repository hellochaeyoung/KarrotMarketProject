package com.project.karrot.src.views;

import com.project.karrot.constants.SessionConstants;
import com.project.karrot.src.annotation.CurrentMemberId;
import com.project.karrot.src.annotation.LoginCheck;
import com.project.karrot.src.category.CategoryService;
import com.project.karrot.src.category.dto.CategoryResponseDto;
import com.project.karrot.src.deal.DealService;
import com.project.karrot.src.deal.dto.DealRequestDto;
import com.project.karrot.src.deal.dto.DealResponseDto;
import com.project.karrot.src.image.FileUploadService;
import com.project.karrot.src.interest.InterestedService;
import com.project.karrot.src.interest.dto.InterestedResponseDto;
import com.project.karrot.src.member.MemberService;
import com.project.karrot.src.member.dto.MemberRequestDto;
import com.project.karrot.src.member.dto.MemberResponseDto;
import com.project.karrot.src.product.ProductService;
import com.project.karrot.src.ProductStatus;
import com.project.karrot.src.category.Category;
import com.project.karrot.src.deal.Deal;
import com.project.karrot.src.interest.InterestedProduct;
import com.project.karrot.src.product.dto.ProductAndCategoryRes;
import com.project.karrot.src.product.dto.ProductAndStatusRequestDto;
import com.project.karrot.src.product.dto.ProductRequestDto;
import com.project.karrot.src.product.dto.ProductResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final MemberService memberService;
    private final ProductService productService;
    private final DealService dealService;
    private final CategoryService categoryService;
    private final InterestedService interestedService;
    private final FileUploadService fileUploadService;

    @ApiOperation(value = "마이페이지 - 프로필 조회", notes = "프로필을 조회한다.")
    @GetMapping("/profile")
    @LoginCheck
    public ResponseEntity<?> profile(@CurrentMemberId Long memberId) {

        MemberResponseDto memberResponseDto = memberService.find(memberId);

        return new ResponseEntity<>(memberResponseDto.getNickName(), HttpStatus.OK);

    }

    @ApiOperation(value = "프로필 이미지 변경", notes = "프로필 이미지를 변경한다.")
    @PostMapping("/profile/image")
    @LoginCheck
    public ResponseEntity<?> profileImage(@CurrentMemberId Long memberId, @RequestPart MultipartFile file) {
        return new ResponseEntity<>(fileUploadService.uploadImage(file), HttpStatus.OK);
    }

    @ApiOperation(value = "마이페이지 - 프로필 수정", notes = "프로필을 수정한다.")
    @PutMapping("/profile")
    @LoginCheck
    public ResponseEntity<?> change(@CurrentMemberId Long memberId, @RequestBody String nickName) {
        return new ResponseEntity<>(memberService.update(memberId, nickName), HttpStatus.OK);
    }

    @ApiOperation(value = "마이페이지 - 등록 상품 목록 조회", notes = "내가 등록한 상품들의 상품 상태별 목록을 조회한다.")
    @GetMapping("/myProducts/status/{status}")
    @LoginCheck
    public ResponseEntity<?> getProductList(@CurrentMemberId Long memberId, @PathVariable String status) {

        List<ProductResponseDto> list = new ArrayList<>();

        if(status.equals("SALE")) { // 판매중
            list = productService.findByMemberAndStatus(memberId, ProductStatus.SALE);
        }else if(status.equals("COMPLETE")){ // 거래완료
            list = productService.findByMemberAndStatus(memberId, ProductStatus.COMPLETE);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @ApiOperation(value = "마이페이지 - 등록 상품 상태 수정", notes = "내가 등록한 상품의 상품 상태를 변경한다.")
    @PostMapping("/myProducts/status")
    @LoginCheck
    public ResponseEntity<?> updateStatus(@CurrentMemberId Long memberId, @RequestBody ProductAndStatusRequestDto product) {

        List<ProductResponseDto> list;

        String status = product.getStatus();

        updateNewStatus(memberId, product, status);

        if(status.equals("RESERVATION")) {
            list = productService.findByMemberAndStatus(memberId, ProductStatus.RESERVATION);
        }else if(status.equals("SALE")){
            list = productService.findByMemberAndStatus(memberId, ProductStatus.SALE);
        }else {
            list = productService.findByMemberAndStatus(memberId, ProductStatus.COMPLETE);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);

    }

    @ApiOperation(value = "마이페이지 - 관심 상품 목록 조회", notes = "관심 등록한 상품 목록을 조회한다.")
    @GetMapping("/myInterests")
    @LoginCheck
    public ResponseEntity<?> getInterestedList(@CurrentMemberId Long memberId) {

        return new ResponseEntity<>(interestedService.findInterestedByMemberAndProductStatus(memberId), HttpStatus.OK);

    }

    @ApiOperation(value = "마이페이지 - 상품 정보 조회", notes = "등록 상품의 정보를 조회한다.")
    @GetMapping("/myProducts/{productId}")
    @LoginCheck
    public ResponseEntity<?> findUpdateProduct(@PathVariable Long productId) {

        List<CategoryResponseDto> categories = categoryService.findAll();
        ProductResponseDto product = productService.findById(productId);

        return new ResponseEntity<>(new ProductAndCategoryRes(categories, product), HttpStatus.OK);
    }

    @ApiOperation(value = "마이페이지 - 등록 상품 수정", notes = "등록한 상품의 정보를 수정한다.")
    @PutMapping("/myProducts/{productId}")
    @LoginCheck
    public ResponseEntity<?> update(@PathVariable Long productId, @RequestBody ProductRequestDto productReq) {
        return new ResponseEntity<>(productService.update(productReq), HttpStatus.OK);
    }

    @ApiOperation(value = "구매 내역 목록 조회", notes = "회원의 구매 내역 목록을 조회한다.")
    @GetMapping("/orders")
    @LoginCheck
    public ResponseEntity<?> viewOrders(@CurrentMemberId Long memberId) {
        return new ResponseEntity<>(dealService.findByMember(memberId), HttpStatus.OK);
    }

    @ApiOperation(value = "구매 상품 상세 조회", notes = "구매 상품을 상세 조회한다.")
    @GetMapping("/orders/{productId}")
    @LoginCheck
    public ResponseEntity<?> viewOrdersDetail(@CurrentMemberId Long memberId, @PathVariable Long productId) {
        return new ResponseEntity<>(productService.findById(productId), HttpStatus.OK);
    }

    public void updateNewStatus(Long memberId, ProductAndStatusRequestDto productAndStatusRequestDto, String status) {

        ProductRequestDto product = productAndStatusRequestDto.getProductRequestDto();

        if(product.getProductStatus().equals(ProductStatus.COMPLETE)) {
            DealResponseDto deal = dealService.findByProduct(product.getProductId());

            dealService.remove(deal.getDealId());

            if(status.equals("DELETE")) {
                productService.remove(product.getProductId());
                return;
            }
        }

        if(status.equals("RESERVATION")) {
            productService.updateStatus(product, ProductStatus.RESERVATION);

        }else if(status.equals("SALE")) {
            productService.updateStatus(product, ProductStatus.SALE);

        }else { // 거래완료로 변경 시
            productService.updateStatus(product, ProductStatus.COMPLETE);
            dealService.register(new DealRequestDto(memberId, product.getProductId()));
        }
    }
}
