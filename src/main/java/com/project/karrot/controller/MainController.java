package com.project.karrot.controller;

import com.project.karrot.dto.*;
import com.project.karrot.service.CategoryService;
import com.project.karrot.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/mains")
public class MainController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @ApiOperation(value = "메인 화면 조회", notes = "유저 거주 지역과 카테고리에 해당하는 상품들을 조회한다.")
    @PostMapping()
    public List<ProductResponseDto> setCategory(@RequestBody LocationRequestDto memberLocation, @RequestBody CategoryRequestDto categoryRequest) {

        return productService.findByLocationAndCategory(memberLocation.getLocationId(), categoryRequest.getCategoryId());
    }

    @ApiOperation(value = "상품 등록 화면 조회", notes = "카테고리 목록을 세팅한다.")
    @GetMapping("/products/new")
    public List<CategoryResponseDto> productRegisterForm() {

        return categoryService.findAll();
    }


    @ApiOperation(value = "상품 등록", notes = "상품을 등록한다.")
    @PostMapping("/products/new")
    public ProductResponseDto register(@RequestBody ProductRequestDto productRequestDto) {

        return productService.register(productRequestDto);
    }

}

