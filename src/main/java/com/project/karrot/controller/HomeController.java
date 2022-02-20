package com.project.karrot.controller;

import com.project.karrot.constants.SessionConstants;
import com.project.karrot.domain.Category;
import com.project.karrot.domain.Member;
import com.project.karrot.domain.Product;
import com.project.karrot.dto.LocationResponseDto;
import com.project.karrot.dto.MemberRequestDto;
import com.project.karrot.dto.MemberResponseDto;
import com.project.karrot.dto.ProductResponseDto;
import com.project.karrot.service.CategoryService;
import com.project.karrot.service.LocationService;
import com.project.karrot.service.MemberService;
import com.project.karrot.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private final MemberService memberService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final LocationService locationService;

    public HomeController(MemberService memberService, ProductService productService, CategoryService categoryService, LocationService locationService) {
        this.memberService = memberService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.locationService = locationService;
    }

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) MemberRequestDto loginMember, Model model) {

        if(loginMember == null) {
            return "home";
        }

        MemberResponseDto memberResponseDto = memberService.findByNickName(loginMember.getNickName());
        model.addAttribute("member", memberResponseDto);

        List<ProductResponseDto> products = productService.findByLocation(memberResponseDto.getLocation());
        model.addAttribute("products", products);

        List<Category> allCategory = categoryService.findAll();
        model.addAttribute("allCategory", allCategory);

        return "mains/mainPage";
    }
}

