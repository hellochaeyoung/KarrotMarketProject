package com.project.karrot.controller;

import com.project.karrot.dto.*;
import com.project.karrot.service.CommentService;
import com.project.karrot.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CommentService commentService;

    @ApiOperation(value = "상품 상세 조회", notes = "상품을 조회한다.")
    @GetMapping("/{productId}")
    public List<ProductResponseDto> viewProduct(@PathVariable Long productId) {

        ProductResponseDto productResponseDto = productService.findById(productId);

        MemberResponseDto productOwner = new MemberResponseDto(productResponseDto.getMember());
        List<ProductResponseDto> productAllList = productService.findByMember(productOwner.getId());
        productAllList.remove(productResponseDto);

        return productAllList;
    }


    @ApiOperation(value = "상품 댓글 조회", notes = "상품에 등록된 댓글을 조회한다.")
    @GetMapping("/{productId}/comments")
    public List<CommentResponseDto> viewComment(@PathVariable Long productId) {

        return commentService.findByProductId(productId);

    }

    @ApiOperation(value = "상품 댓글 등록", notes = "상품에 댓글을 등록한다.")
    @PostMapping("/{productId}/comments")
    public CommentResponseDto registerComment(@RequestBody CommentRequestDto commentRequestDto,
                                  @PathVariable Long productId) {

        ProductResponseDto productResponseDto = productService.findById(productId);

        MemberResponseDto memberResponseDto = new MemberResponseDto(productResponseDto.getMember());

        //수정 필요
        //comment.setContents(commentForm.getContents());
        //comment.setMember(loginMember);
        //comment.setProduct(product);

        return commentService.register(commentRequestDto, memberResponseDto);

    }
}
