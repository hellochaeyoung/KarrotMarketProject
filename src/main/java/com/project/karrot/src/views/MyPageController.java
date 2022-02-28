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
import com.project.karrot.src.product.dto.ProductRequestDto;
import com.project.karrot.src.product.dto.ProductResponseDto;
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

    @PutMapping("/profile")
    public MemberResponseDto change(@PathVariable Long memberId, @RequestBody String nickName) {
        return memberService.update(memberId, nickName);
    }

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

    @GetMapping("/products/{productId}")
    public String findUpdateProduct(@PathVariable Long productId) {

        //model.addAttribute("allCategory", categoryService.findAll());
        //model.addAttribute("product", productService.findById(productId));

        List<CategoryResponseDto> categories = categoryService.findAll();
        ProductResponseDto product = productService.findById(productId);


        return "products/update";
    }

    @PostMapping("/products/update")
    public String update(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) MemberRequestDto loginMember, Model model, ProductRequestDto productRequestDto) {

        ProductResponseDto product = productService.findById(productRequestDto.getProductId());
        Category category = categoryService.findByName(productRequestDto.getCategoryName()).get();

        /* update 로직 서비스단에서 처리할 것
        product.setProductName(productForm.getProductName());
        product.setCategory(category);
        product.setPrice(productForm.getPrice());
        product.setContents(productForm.getContents());
         */

        // 수정 필요
        // setNewStatus(loginMember,product, productRequestDto.getStatus());

        model.addAttribute("status", "SALE");

        return "mine/myProductList";
    }

    public void setNewStatus(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) MemberRequestDto loginMember,
                             ProductRequestDto product, String status) {

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
            productService.register(product, loginMember); // 업데이트

        }else if(status.equals("SALE")) {
            product.setProductStatus(ProductStatus.SALE);
            productService.register(product, loginMember); // 업데이트

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
