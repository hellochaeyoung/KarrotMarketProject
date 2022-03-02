package com.project.karrot.src.views;

import com.project.karrot.common.Api.Response;
import com.project.karrot.src.category.CategoryService;
import com.project.karrot.src.category.dto.CategoryResponseDto;
import com.project.karrot.src.location.LocationService;
import com.project.karrot.src.location.dto.LocationResponseDto;
import com.project.karrot.src.member.MemberService;
import com.project.karrot.src.member.dto.MemberRequestDto;
import com.project.karrot.src.member.dto.MemberResponseDto;
import com.project.karrot.src.product.ProductService;
import com.project.karrot.src.product.dto.ProductResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/home")
public class HomeController {

    private final MemberService memberService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final LocationService locationService;

    @ApiOperation(value = "로그인 및 회원가입 페이지", notes = "페이지 접속 시 처음 보여지는 화면 입니다.")
    @GetMapping()
    public ResponseEntity<?> home() {
        return Response.success("hello", HttpStatus.OK).reponseBuild();
    }

    @ApiOperation(value = "회원 가입 - 지역 조회", notes = "회원 가입 시 거주 지역을 검색한다")
    @GetMapping("/signUp/{address}")
    public List<LocationResponseDto> getAll(@PathVariable String address) {

        return locationService.findByAddressAll(address);
    }

    @ApiOperation(value = "회원 가입", notes = "회원 가입을 한다.")
    @PostMapping("/signUp")
    public ResponseEntity<?> create(@RequestBody MemberRequestDto memberRequestDto) {
        ;
        // 회원가입 예외처리 추가 필요

        return new ResponseEntity<>(memberService.join(memberRequestDto), HttpStatus.OK);
    }

}

