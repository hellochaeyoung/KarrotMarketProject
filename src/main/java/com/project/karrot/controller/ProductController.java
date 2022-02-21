package com.project.karrot.controller;

import com.project.karrot.constants.SessionConstants;
import com.project.karrot.domain.*;
import com.project.karrot.dto.CommentRequestDto;
import com.project.karrot.dto.MemberRequestDto;
import com.project.karrot.dto.MemberResponseDto;
import com.project.karrot.dto.ProductResponseDto;
import com.project.karrot.service.CategoryService;
import com.project.karrot.service.CommentService;
import com.project.karrot.service.MemberService;
import com.project.karrot.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class ProductController {

    private final MemberService memberService;
    private final ProductService productService;
    private final CommentService commentService;


    public ProductController(MemberService memberService, ProductService productService, CommentService commentService) {
        this.memberService = memberService;
        this.productService = productService;
        this.commentService = commentService;
    }

    @GetMapping("/products/view")
    public String viewProduct(Model model, Long productId) {

        ProductResponseDto productResponseDto = productService.findById(productId);
        model.addAttribute("product", productResponseDto);

        MemberResponseDto productOwner = new MemberResponseDto(productResponseDto.getMember());
        List<ProductResponseDto> productAllList = productService.findByMember(productOwner.getId());
        productAllList.remove(productResponseDto);
        model.addAttribute("otherProducts", productAllList);

        model.addAttribute("productOwner", productOwner);

        return "products/view";
    }


    @GetMapping("/products/comment")
    public String viewComment(Model model, Long productId) {

        ProductResponseDto product = productService.findById(productId);
        List<Comment> comments = commentService.findByProductId(product.getProductId()).orElseGet(ArrayList::new);

        model.addAttribute("comments", comments);
        model.addAttribute("product", product);

        return "products/comment";

    }

    @PostMapping("/products/comment")
    public String registerComment(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) MemberResponseDto loginMember, CommentRequestDto commentRequestDto,
                                  Long productId) {

        ProductResponseDto productResponseDto = productService.findById(productId);

        //Comment comment = new Comment();

        //MemberResponseDto memberResponseDto = memberService.findByNickName(loginMember.getNickName());

        //existId.ifPresent(comment::setCommentId);

        //수정 필요
        //comment.setContents(commentForm.getContents());
        //comment.setMember(loginMember);
        //comment.setProduct(product);

        commentService.register(commentRequestDto, loginMember);

        return "redirect:/products/comment?productId=" + productId;

    }

    @GetMapping("/products/all")
    public String all(Model model, String nickName, Product product) {

        MemberResponseDto memberResponseDto = memberService.findByNickName(nickName);

        List<ProductResponseDto> products = productService.findByMember(memberResponseDto.getId());
        model.addAttribute("products", products);

        model.addAttribute("member", memberResponseDto);
        model.addAttribute("product", product);

        return "products/all";
    }

    @PostMapping("/products/all")
    public String allByStatus(Model model, StatusProductForm statusProductForm) {

        List<ProductResponseDto> list = new ArrayList<>();

        String status = statusProductForm.getStatus();
        Long memberId = Long.parseLong(statusProductForm.getMemberId());

        MemberResponseDto memberResponseDto = memberService.find(memberId);

        if(status.equals("ALL")) {
            list = productService.findByMember(memberResponseDto.getId());
        }else if(status.equals("SALE")) {
            list = productService.findByMemberAndStatus(memberResponseDto.getId(), ProductStatus.SALE);
        }else {
            /* 수정 필요
            for(Deal deal : memberResponseDto.getDeals()) {
                list.add(deal.getProduct());
            }

             */
        }

        model.addAttribute("products", list);
        model.addAttribute("member", memberResponseDto);

        return "products/all";
    }
}
