package com.project.karrot.src.views;

import com.project.karrot.src.annotation.CurrentMemberId;
import com.project.karrot.src.annotation.LoginCheck;
import com.project.karrot.src.category.CategoryService;
import com.project.karrot.src.category.dto.CategoryAndLocationRequestDto;
import com.project.karrot.src.category.dto.CategoryRequestDto;
import com.project.karrot.src.category.dto.CategoryResponseDto;
import com.project.karrot.src.location.LocationService;
import com.project.karrot.src.location.dto.LocationRequestDto;
import com.project.karrot.src.member.Member;
import com.project.karrot.src.member.MemberService;
import com.project.karrot.src.member.dto.MemberResponseDto;
import com.project.karrot.src.product.ProductService;
import com.project.karrot.src.product.dto.ProductAndCategoryRes;
import com.project.karrot.src.product.dto.ProductRequestDto;
import com.project.karrot.src.product.dto.ProductResponseDto;
import io.jsonwebtoken.lang.Collections;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final MemberService memberService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final LocationService locationService;

    @ApiOperation(value = "메인 화면 조회", notes = "지역과 설정한 카테고리에 해당하는 상품들을 조회한다.")
    @PostMapping("/")
    @LoginCheck
    public ResponseEntity<?> viewProduct(@CurrentMemberId Long memberId, @RequestBody CategoryAndLocationRequestDto categoryAndLocationRequestDto) {

        List<CategoryResponseDto> categoryList = categoryService.findAll();
        List<ProductResponseDto> productList = productService.findByLocationAndCategory(categoryAndLocationRequestDto.getLocationId(), categoryAndLocationRequestDto.getCategoryId());

        return new ResponseEntity<>(new ProductAndCategoryRes(categoryList, productList), HttpStatus.OK);
    }

    @ApiOperation(value = "메인 화면 - 지역 검색", notes = "지역을 검색한다")
    @PostMapping("/location")
    @LoginCheck
    public ResponseEntity<?> getLocation(@RequestBody String locationName) {
        return new ResponseEntity<>(locationService.findByAddressAll(locationName), HttpStatus.OK);
    }


    @ApiOperation(value = "상품 등록 화면 조회", notes = "카테고리 목록 데이터를 가져와 보여준다.")
    @GetMapping("/products/new")
    @LoginCheck
    public ResponseEntity<?> productRegisterForm() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }


    @ApiOperation(value = "상품 등록", notes = "새 상품을 등록한다.")
    @PostMapping("/products/new")
    @LoginCheck
    public ResponseEntity<?> register(@CurrentMemberId Long memberId, @RequestBody ProductRequestDto productRequestDto) {
        productRequestDto.setMemberId(memberId); // 추후에 수정할 것
        return new ResponseEntity<>(productService.register(productRequestDto), HttpStatus.OK);
    }

}

