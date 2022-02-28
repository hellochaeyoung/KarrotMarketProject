package com.project.karrot.src.views;

import com.project.karrot.src.category.CategoryService;
import com.project.karrot.src.category.dto.CategoryRequestDto;
import com.project.karrot.src.category.dto.CategoryResponseDto;
import com.project.karrot.src.location.dto.LocationRequestDto;
import com.project.karrot.src.product.ProductService;
import com.project.karrot.src.product.dto.ProductAndCategoryRes;
import com.project.karrot.src.product.dto.ProductRequestDto;
import com.project.karrot.src.product.dto.ProductResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @ApiOperation(value = "메인 화면 조회", notes = "거주 지역과 설정한 카테고리에 해당하는 상품들을 조회한다.")
    @PostMapping("/")
    public ProductAndCategoryRes setCategory(@RequestBody LocationRequestDto memberLocation, @RequestBody CategoryRequestDto categoryRequest) {

        List<CategoryResponseDto> categoryList = categoryService.findAll();
        List<ProductResponseDto> productList = productService.findByLocationAndCategory(memberLocation.getLocationId(), categoryRequest.getCategoryId());

        return new ProductAndCategoryRes(categoryList, productList);
    }

    @ApiOperation(value = "상품 등록 화면 조회", notes = "카테고리 목록을 세팅한다.")
    @GetMapping("/products/new")
    public List<CategoryResponseDto> productRegisterForm() {

        return categoryService.findAll();
    }


    @ApiOperation(value = "상품 등록", notes = "새 상품을 등록한다.")
    @PostMapping("/products/new")
    public ProductResponseDto register(@RequestBody ProductRequestDto productRequestDto) {

        return productService.register(productRequestDto);
    }

}

