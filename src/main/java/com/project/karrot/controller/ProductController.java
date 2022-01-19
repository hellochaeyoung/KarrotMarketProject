package com.project.karrot.controller;

import com.project.karrot.constants.SessionConstants;
import com.project.karrot.domain.*;
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

        Product product = productService.find(productId).orElseGet(Product::new);
        model.addAttribute("product", product);

        Member productOwner = product.getMember();
        List<Product> productList = productService.findByMember(productOwner).orElseGet(ArrayList::new);
        productList.remove(product);
        model.addAttribute("otherProducts", productList);

        model.addAttribute("productOwner", productOwner);
        //model.addAttribute("address", productOwner.getLocation().getAddress());

        return "/products/view";
    }

    @GetMapping("/products/comment")
    public String viewComment(Model model, Long productId) {

        Product product = productService.find(productId).orElseGet(Product::new);
        List<Comment> comments = commentService.findByProduct(product).orElseGet(ArrayList::new);

        model.addAttribute("comments", comments);
        model.addAttribute("product", product);

        return "/products/comment";

    }

    @PostMapping("/products/comment")
    public String registerComment(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember, CommentForm commentForm,
                                  Long productId, Model model) {

        Product product = productService.find(productId).orElseGet(Product::new);

        Comment comment = new Comment();

        Optional<Long> existId = commentService.exist(loginMember, product);
        existId.ifPresent(comment::setCommentId);

        comment.setContents(commentForm.getContents());
        comment.setMember(loginMember);
        comment.setProduct(product);

        commentService.register(comment);

        return "redirect:/products/comment?productId=" + productId;

    }

    @GetMapping("/products/all")
    public String all(Model model, String nickName, Product product) {

        Member member = memberService.findByNickName(nickName).orElseGet(Member::new);

        //List<Product> products = productService.findByMember(member).orElseGet(ArrayList::new);

        System.out.println("$$$$$$$$$$$$$$" + nickName);
        List<Product> products = productService.findByMember(member).orElseGet(ArrayList::new);
        model.addAttribute("products", products);

        model.addAttribute("member", member);
        model.addAttribute("product", product);

        return "/products/all";
    }

    @PostMapping("/products/all")
    public String allByStatus(Model model, StatusProductForm statusProductForm) {

        List<Product> list = new ArrayList<>();

        //System.out.println(map.get("member"));
        //String status = map.get("status").toString();
        //Member member = map.get("member");
        System.out.println("%%%%%%%%%%%" + statusProductForm.getStatus());
        System.out.println("%%%%%%%%%%%" + statusProductForm.getMemberId());

        String status = statusProductForm.getStatus();
        Long memberId = Long.parseLong(statusProductForm.getMemberId());

        Member member = memberService.find(memberId).orElseGet(Member::new);

        System.out.println("%%%%%%%%%" + status);
        System.out.println("%%%%%%%%%" + member.getId());
        if(status.equals("ALL")) {
            list = productService.findByMember(member).orElseGet(ArrayList::new);
        }else if(status.equals("SALE")) {
            list = productService.findByMemberAndStatus(member, ProductStatus.SALE).orElseGet(ArrayList::new);
        }else {
            for(Deal deal : member.getDeals()) {
                list.add(deal.getProduct());
            }
        }

        model.addAttribute("products", list);

        return "/products/all :: #resultTable";
    }
}
