package com.project.karrot.controller;

import com.project.karrot.constants.SessionConstants;
import com.project.karrot.domain.*;
import com.project.karrot.dto.LocationResponseDto;
import com.project.karrot.dto.MemberRequestDto;
import com.project.karrot.dto.MemberResponseDto;
import com.project.karrot.dto.ProductResponseDto;
import com.project.karrot.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final LocationService locationService;
    private final ProductService productService;

    @GetMapping("/new/{address}")
    public List<LocationResponseDto> getAll(@ApiParam(value = "거주 지역 검색", required = false) @PathVariable String address) {

        return locationService.findByAddressAll(address);
    }

    @PostMapping("/new")
    public MemberResponseDto create(@ApiParam(value = "회원가입 입력 정보", required = true) @RequestBody MemberRequestDto memberRequestDto) {

        return memberService.join(memberRequestDto);
    }

    @PostMapping("/login")
    public String login(@RequestBody MemberRequestDto memberRequestDto,
                        BindingResult bindingResult, HttpServletRequest request) {

        if(bindingResult.hasErrors()) {
            return "members/loginForm";
        }

        MemberResponseDto loginMember = memberService.login(memberRequestDto);

        if(loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "members/loginForm";
        }

        //productService.findByMember(loginMember).ifPresent(loginMember::setProducts);
        //dealService.findByMember(loginMember).ifPresent(loginMember::setDeals);
        //interestedService.findInterestedByMember(loginMember).ifPresent(loginMember::setInterestedProducts);

        //Location location = locationService.find(loginMember.getLocation().getLocationId()).get();
        //loginMember.setLocation(location);

        HttpSession session = request.getSession(); // 세션 있으면 반환, 없으면 신규 세션 생성
        session.setAttribute(SessionConstants.LOGIN_MEMBER, loginMember); // 세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConstants.LOGIN_MEMBER_LOCATION, loginMember.getLocation());

        return "redirect:/";
    }

    @ApiOperation(value = "유저 등록 상품 목록 조회", notes = "상품 등록자의 모든 등록된 상품을 조회한다.")
    @PostMapping("/{memberId}/products/{status}")
    public List<ProductResponseDto> allByStatus(@PathVariable Long memberId, @PathVariable String status) {

        List<ProductResponseDto> list = new ArrayList<>();

        if(status.equals("ALL")) {
            list = productService.findByMember(memberId);
        }else if(status.equals("SALE")) {
            list = productService.findByMemberAndStatus(memberId, ProductStatus.SALE);
        }else {
            /* 수정 필요
            for(Deal deal : memberResponseDto.getDeals()) {
                list.add(deal.getProduct());
            }

             */
        }

        return list;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate(); // 세션 날리기
        }

        return "redirect:/";

    }
}

