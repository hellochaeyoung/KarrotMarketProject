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
import com.project.karrot.src.image.dto.ImageUpdateRequestDto;
import com.project.karrot.src.interest.InterestedService;
import com.project.karrot.src.interest.dto.InterestedResponseDto;
import com.project.karrot.src.member.MemberService;
import com.project.karrot.src.member.dto.MemberAndImageResponseDto;
import com.project.karrot.src.member.dto.MemberRequestDto;
import com.project.karrot.src.member.dto.MemberResponseDto;
import com.project.karrot.src.memberimage.MemberImageService;
import com.project.karrot.src.memberimage.dto.MemberImageRequestDto;
import com.project.karrot.src.product.ProductService;
import com.project.karrot.src.ProductStatus;
import com.project.karrot.src.category.Category;
import com.project.karrot.src.deal.Deal;
import com.project.karrot.src.interest.InterestedProduct;
import com.project.karrot.src.product.dto.*;
import com.project.karrot.src.productimage.ProductImageService;
import com.project.karrot.src.productimage.dto.ProductImageDto;
import com.project.karrot.src.productimage.dto.ProductImageRequestDto;
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
    private final MemberImageService memberImageService;
    private final ProductImageService productImageService;

    @ApiOperation(value = "마이페이지 - 프로필 조회", notes = "프로필을 조회한다.")
    @GetMapping("/profile")
    @LoginCheck
    public ResponseEntity<?> profile(@CurrentMemberId Long memberId) {
        //MemberResponseDto memberResponseDto = memberService.find(memberId);
        MemberAndImageResponseDto memberAndImageResponseDto = memberService.findWithImage(memberId);

        return new ResponseEntity<>(memberAndImageResponseDto, HttpStatus.OK);

    }

    @ApiOperation(value = "프로필 이미지 변경", notes = "프로필 이미지를 변경한다.")
    @PostMapping("/profile/image")
    @LoginCheck
    public ResponseEntity<?> profileImage(@CurrentMemberId Long memberId, @RequestPart MultipartFile file) {
        String url = fileUploadService.uploadImage(file);

        MemberImageRequestDto memberImageRequestDto = new MemberImageRequestDto();
        memberImageRequestDto.toReady(memberId, url);

        return new ResponseEntity<>(memberImageService.save(memberImageRequestDto), HttpStatus.OK);
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

    @ApiOperation(value = "마이페이지 - 등록 상품 조회", notes = "등록한 상품의 정보를 조회한다.")
    @GetMapping("/myProducts/{productId}")
    @LoginCheck
    public ResponseEntity<?> findUpdateProduct(@PathVariable Long productId) {

        List<CategoryResponseDto> categories = categoryService.findAll();
        ProductResponseDto product = productService.findById(productId);

        return new ResponseEntity<>(new ProductAndCategoryRes(categories, product), HttpStatus.OK);
    }

    @ApiOperation(value = "마이페이지 - 등록 상품 수정", notes = "등록한 상품의 정보를 수정한다.")
    @PutMapping("/myProducts")
    @LoginCheck
    public ResponseEntity<?> update(@RequestPart ProductUpdateRequestDto productUpdateRequestDto,
                                    @RequestPart(required = false) List<MultipartFile> fileList) {

        List<ProductImageDto> removeList = productUpdateRequestDto.getRemoveImageList();

        //AWS S3 이미지 업데이트 처리 -> 삭제 후 추가
        removeImage(removeList);
        List<String> urlList = saveImageToAwsS3(fileList);

        // DB 상품이미지 테이블 업데이트 처리 -> 삭제 후 추가
        // 1. 여러 개 이미지 다 변경 2. 일부만 변경 3. 아예 변경 안함
        // 이렇게 세 가지 경우로 나뉘기 때문에 삭제할 이미지들을 [id, fileURL]의 dto로 받아 id 값으로 삭제하고
        // 새로 추가된 fileList를 s3에 넣어 URL 받아온 후 DB에 저장한다. <- productService의 update 메소드에서 수행
        removeList.listIterator().forEachRemaining(productImageDto -> {
            productImageService.delete(productImageDto.getId());
        });

        productUpdateRequestDto.toReady(urlList);
        return new ResponseEntity<>(productService.update(productUpdateRequestDto), HttpStatus.OK);
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

    /**
     * 상품 이미지 변경으로 인한 AWS S3에서 기존 이미지 삭제 메소드
     * @param removeList 삭제할 이미지 url
     */
    public void removeImage(List<ProductImageDto> removeList) {
        removeList.listIterator().forEachRemaining(productImageDto -> {
            System.out.println("remove url from AWS S3 : " + productImageDto.getFileURL());
            fileUploadService.delete(productImageDto.getFileURL());
        });
    }

    /**
     * AWS S3에 상품 이미지 저장 메소드
     * @param fileList 새로 저장할 이미지 파일 리스트
     * @return url 리스트
     */
    public List<String> saveImageToAwsS3(List<MultipartFile> fileList) {
        List<String> urlList = new ArrayList<>();
        fileList.listIterator().forEachRemaining(file -> {
            String fileURL = fileUploadService.uploadImage(file);
            urlList.add(fileURL);
        });

        return urlList;
    }
}
