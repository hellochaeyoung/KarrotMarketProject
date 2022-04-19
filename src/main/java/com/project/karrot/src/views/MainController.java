package com.project.karrot.src.views;

import com.project.karrot.src.annotation.CurrentMemberId;
import com.project.karrot.src.annotation.LoginCheck;
import com.project.karrot.src.category.CategoryService;
import com.project.karrot.src.category.dto.CategoryAndLocationRequestDto;
import com.project.karrot.src.category.dto.CategoryResponseDto;
import com.project.karrot.src.image.FileUploadService;
import com.project.karrot.src.location.LocationService;
import com.project.karrot.src.member.MemberService;
import com.project.karrot.src.product.ProductService;
import com.project.karrot.src.product.dto.ProductAndCategoryRes;
import com.project.karrot.src.product.dto.ProductAndImageResponseDto;
import com.project.karrot.src.product.dto.ProductRequestDto;
import com.project.karrot.src.product.dto.ProductResponseDto;
import com.project.karrot.src.productimage.ProductImageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final MemberService memberService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final LocationService locationService;
    private final ProductImageService productImageService;
    private final FileUploadService fileUploadService;

    @ApiOperation(value = "메인 화면 조회", notes = "지역과 설정한 카테고리에 해당하는 상품들을 조회한다.")
    @PostMapping("/")
    @LoginCheck
    public ResponseEntity<?> viewProduct(@RequestBody CategoryAndLocationRequestDto categoryAndLocationRequestDto) {

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
    public ResponseEntity<?> register(@CurrentMemberId Long memberId,
                                      @RequestPart ProductRequestDto productRequestDto,
                                      BindingResult bindingResult,
                                      @RequestPart(required = false) List<MultipartFile> fileList) {

        // 상품 등록 시 입력값 예외처리
        // 상품명(productName), 카테고리명(categoryName), 가격(price), 설명(contents) 반드시 필요
        // 가격은 숫자형만 가능, 범위는 100원 ~ 200만원까지 입력 가능
        /*if(StringUtils.hasText(productRequestDto.getProductName())) {
            //bindingResult.addError(new FieldError("productRequestDto", "productName", productRequestDto.getProductName(), false, new String[]{"required.productRequestDto.productName"}, null, "상품명은 필수입니다."));
            bindingResult.rejectValue("productName", "required");
        }
         */
        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "productName", "required");

        /*
        if(StringUtils.hasText(productRequestDto.getCategoryName())) {
            //bindingResult.addError(new FieldError("productRequestDto", "categoryName", productRequestDto.getCategoryName(), false, new String[]{"required.productRequestDto.categoryName"}, null, "카테고리명은 필수입니다."));
            bindingResult.rejectValue("categoryName", "required");
        }
         */
        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "categoryName", "required");

        /*if(StringUtils.hasText(productRequestDto.getContents())) {
            //bindingResult.addError(new FieldError("productRequestDto", "contents", productRequestDto.getContents(), false, new String[]{"required.productRequestDto.contents"}, null, "상품 설명은 필수입니다."));
            bindingResult.rejectValue("contents", "required");
        }
         */
        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "contents", "required");


        if(productRequestDto.getPrice() == null || productRequestDto.getPrice() < 100 || productRequestDto.getPrice() > 2000000) {
            //bindingResult.addError(new FieldError("productRequestDto", "price", productRequestDto.getPrice(), false, new String[]{"range.productRequestDto.price"}, new Object[]{100, 2000000},"가격은 100원~ 200만원까지 입력 가능합니다."));
            bindingResult.rejectValue("price", "range", new Object[]{100, 2000000}, null);
        }

        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult, HttpStatus.BAD_REQUEST);
        }

        List<String> fileUrlList = new ArrayList<>();

        if(fileList != null) {
            fileList.listIterator().forEachRemaining(file -> {
                String url = fileUploadService.uploadImage(file);
                fileUrlList.add(url);
            });
        }

        productRequestDto.toReady(memberId, fileUrlList);
        ProductAndImageResponseDto productAndImageResponseDto = productService.register(productRequestDto);

        return new ResponseEntity<>(productAndImageResponseDto, HttpStatus.OK);
    }

}

