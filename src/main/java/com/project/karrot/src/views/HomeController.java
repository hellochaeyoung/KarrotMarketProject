package com.project.karrot.src.views;

import com.project.karrot.src.category.CategoryService;
import com.project.karrot.src.category.dto.CategoryResponseDto;
import com.project.karrot.src.location.LocationService;
import com.project.karrot.src.member.MemberService;
import com.project.karrot.src.member.dto.MemberRequestDto;
import com.project.karrot.src.member.dto.MemberResponseDto;
import com.project.karrot.src.product.ProductService;
import com.project.karrot.src.product.dto.ProductResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

