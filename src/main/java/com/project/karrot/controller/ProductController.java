package com.project.karrot.controller;

import com.project.karrot.constants.SessionConstants;
import com.project.karrot.domain.Comment;
import com.project.karrot.domain.Member;
import com.project.karrot.domain.Product;
import com.project.karrot.service.CommentService;
import com.project.karrot.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CommentService commentService;

    public ProductController(ProductService productService, CommentService commentService) {
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

        model.addAttribute("nickName", productOwner.getNickName());
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
                                  Long productId) {

        Product product = productService.find(productId).orElseGet(Product::new);

        System.out.println("############## " + productId);
        Comment comment = new Comment();
        comment.setContents(commentForm.getContents());
        comment.setMember(loginMember);
        comment.setProduct(product);

        commentService.register(comment);

        return "/products/comment";

    }
}
