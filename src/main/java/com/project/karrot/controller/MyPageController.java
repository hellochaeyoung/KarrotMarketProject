package com.project.karrot.controller;

import com.project.karrot.constants.SessionConstants;
import com.project.karrot.domain.*;
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

        return "/members/myPage";
    }

    @GetMapping("/mine/profile")
    public String profile(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember,
                          Model model) {

        model.addAttribute("nickName", loginMember.getNickName());

        return "/mine/profile";

    }

    @PostMapping("/mine/profile")
    public String change(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember,
                          Model model, String nickName) {

        loginMember.setNickName(nickName);
        memberService.join(loginMember);

        model.addAttribute("nickName", loginMember.getNickName());

        return "/mine/profile";

    }

    @PostMapping("/mine/myProductList")
    public String getProductList(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember,
                                 String status, Model model) {

        System.out.println("@@@@@@@@@@" + status);
        List<Product> list = new ArrayList<>();

        if(status.equals("SALE")) { // 판매중
            list = productService.findByMemberAndStatus(loginMember, ProductStatus.SALE).orElseGet(ArrayList::new);
        }else if(status.equals("COMPLETE")){ // 거래완료
            for(Deal deal : loginMember.getDeals()) {
                list.add(deal.getProduct());
                System.out.println(deal.getProduct());
            }
        }


        model.addAttribute("products", list);

        return "mine/myProductList";
    }

    @PostMapping("/updateStatus")
    public String updateStatus(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember,
                               String updateStatus, Long productId, Model model) {

        System.out.println("#############" + updateStatus);
        System.out.println("#############" + productId);
        List<Product> list = new ArrayList<>();

        Product product = productService.find(productId).get();

        setNewStatus(loginMember, product, updateStatus);

        if(updateStatus.equals("RESERVATION")) {
            list = productService.findByMemberAndStatus(loginMember, ProductStatus.SALE).orElseGet(ArrayList::new);
        }else {
            for(Deal d : loginMember.getDeals()) {
                list.add(d.getProduct());
            }
        }

        model.addAttribute("products", list);

        return "/mine/myProductList";

    }

    @GetMapping("/mine/myInterestedList")
    public String getInterestedList(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember,
                                    Model model) {

        model.addAttribute("interestedProducts",loginMember.getInterestedProducts());

        return "mine/myInterestedList";

    }

    @GetMapping("/products/update")
    public String findUpdateProduct(Model model, Long productId) {

        model.addAttribute("allCategory", categoryService.findAll());
        model.addAttribute("product", productService.find(productId).get());

        return "/products/update";
    }

    @PostMapping("/products/update")
    public String update(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember, Model model, ProductForm productForm) {

        Product product = productService.find(productForm.getProductId()).get();
        Category category = categoryService.findByName(productForm.getCategory()).get();

        product.setProductName(productForm.getProductName());
        product.setCategory(category);
        product.setPrice(productForm.getPrice());
        product.setContents(productForm.getContents());

        setNewStatus(loginMember,product, productForm.getStatus());

        model.addAttribute("status", "SALE");

        return "mine/myProductList";
    }

    public void setNewStatus(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember,
                             Product product, String status) {

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
            Deal deal = dealService.findByProduct(product).get();
            dealService.remove(deal);
        }

        if(status.equals("RESERVATION")) {
            product.setProductStatus(ProductStatus.RESERVATION);
            productService.register(product); // 업데이트

        }else if(status.equals("SALE")) {
            product.setProductStatus(ProductStatus.SALE);
            productService.register(product); // 업데이트

        }else {
            product.setProductStatus(ProductStatus.COMPLETE);
            Product result = productService.register(product);

            Deal deal = new Deal();
            deal.setMember(loginMember);
            deal.setProduct(result);

            dealService.register(deal);

        }
    }
}
