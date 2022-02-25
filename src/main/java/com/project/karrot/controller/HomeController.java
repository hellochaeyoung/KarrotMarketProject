package com.project.karrot.controller;

import com.project.karrot.constants.SessionConstants;
import com.project.karrot.domain.Category;
import com.project.karrot.domain.Member;
import com.project.karrot.domain.Product;
import com.project.karrot.dto.*;
import com.project.karrot.service.CategoryService;
import com.project.karrot.service.LocationService;
import com.project.karrot.service.MemberService;
import com.project.karrot.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class HomeController {

    private final MemberService memberService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final LocationService locationService;

    @ApiOperation(value = "로그인 및 회원가입 페이지", notes = "페이지 접속 시 처음 보여지는 화면 입니다.")
    @GetMapping()
    public String home(MemberRequestDto loginMember, Model model) {

        if(loginMember == null) {
            return "home";
        }

        MemberResponseDto memberResponseDto = memberService.findByNickName(loginMember.getNickName());
        model.addAttribute("member", memberResponseDto);

        List<ProductResponseDto> products = productService.findByLocation(memberResponseDto.getLocation().getLocationId());
        model.addAttribute("products", products);

        List<CategoryResponseDto> allCategory = categoryService.findAll();
        model.addAttribute("allCategory", allCategory);

        return "mains/mainPage";
    }
}

