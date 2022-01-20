package com.project.karrot.controller;

import com.project.karrot.constants.SessionConstants;
import com.project.karrot.domain.Deal;
import com.project.karrot.domain.Member;
import com.project.karrot.domain.Product;
import com.project.karrot.domain.ProductStatus;
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

    public MyPageController(MemberService memberService, ProductService productService) {
        this.memberService = memberService;
        this.productService = productService;
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

        model.addAttribute("nickName", loginMember.getNickName());

        return "/mine/profile";

    }

    @PostMapping("/mine/myProductList")
    public String getProductList(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember,
                                 String status, Model model) {

        List<Product> list = new ArrayList<>();

        if(status.equals("SALE")) { // 판매중
            list = productService.findByMemberAndStatus(loginMember, ProductStatus.SALE).orElseGet(ArrayList::new);
        }else { // 거래완료
            for(Deal deal : loginMember.getDeals()) {
                list.add(deal.getProduct());
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

}
