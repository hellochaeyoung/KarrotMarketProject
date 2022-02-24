package com.project.karrot.controller;

import com.project.karrot.constants.SessionConstants;
import com.project.karrot.domain.*;
import com.project.karrot.dto.MemberRequestDto;
import com.project.karrot.dto.MemberResponseDto;
import com.project.karrot.dto.ProductRequestDto;
import com.project.karrot.dto.ProductResponseDto;
import com.project.karrot.service.CategoryService;
import com.project.karrot.service.DealService;
import com.project.karrot.service.MemberService;
import com.project.karrot.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MyPageController {

    private final MemberService memberService;
    private final ProductService productService;
    private final DealService dealService;
    private final CategoryService categoryService;

    public MyPageController(MemberService memberService, ProductService productService, DealService dealService,
                            CategoryService categoryService) {
        this.memberService = memberService;
        this.productService = productService;
        this.dealService = dealService;
        this.categoryService = categoryService;
    }

    @GetMapping("/members/myPage")
    public String myPage() {

        return "members/myPage";
    }

    @GetMapping("/mine/profile")
    public String profile(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) MemberRequestDto loginMember,
                          Model model) {

        model.addAttribute("nickName", loginMember.getNickName());

        return "mine/profile";

    }

    @PostMapping("/mine/profile")
    public String change(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) MemberRequestDto loginMember,
                          Model model, String nickName) {

        loginMember.setNickName(nickName); //////// 수정 필요
        memberService.join(loginMember);

        model.addAttribute("nickName", loginMember.getNickName());

        return "mine/profile";

    }

    @PostMapping("/mine/myProductList")
    public String getProductList(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) MemberRequestDto loginMember,
                                 String status, Model model) {

        List<ProductResponseDto> list = new ArrayList<>();

        MemberResponseDto member = memberService.findByNickName(loginMember.getNickName()); ////// 수정 필요

        if(status.equals("SALE")) { // 판매중
            list = productService.findByMemberAndStatus(member.getId(), ProductStatus.SALE);
        }else if(status.equals("COMPLETE")){ // 거래완료
            for(Deal deal : member.getDeals()) {
                ProductResponseDto productResponseDto = new ProductResponseDto(deal.getProduct());
                list.add(productResponseDto);
                System.out.println(productResponseDto.getProductName());
            }
        }


        model.addAttribute("products", list);

        return "mine/myProductList";
    }

    @PostMapping("/updateStatus")
    public String updateStatus(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) MemberRequestDto loginMember,
                               String updateStatus, Long productId, Model model) {

        List<ProductResponseDto> list = new ArrayList<>();

        ProductResponseDto product = productService.findById(productId);

        MemberResponseDto member = memberService.findByNickName(loginMember.getNickName());

        // 수정 필요
        //setNewStatus(loginMember, product, updateStatus);

        if(updateStatus.equals("RESERVATION")) {
            list = productService.findByMemberAndStatus(member.getId(), ProductStatus.SALE);
        }else {
            for(Deal d : member.getDeals()) {
                ProductResponseDto productResponseDto = new ProductResponseDto(d.getProduct());
                list.add(productResponseDto);
            }
        }

        model.addAttribute("products", list);

        return "mine/myProductList";

    }

    @PostMapping("/mine/myInterestedList")
    public String getInterestedList(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) MemberRequestDto loginMember,
                                    Model model, String status) {

        MemberResponseDto member = memberService.findByNickName(loginMember.getNickName());

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

        model.addAttribute("interestedProducts", list);

        return "mine/myInterestedList";

    }

    @GetMapping("/products/update")
    public String findUpdateProduct(Model model, Long productId) {

        model.addAttribute("allCategory", categoryService.findAll());
        model.addAttribute("product", productService.findById(productId));

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
