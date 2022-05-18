package com.project.karrot.src.product;

import com.project.karrot.src.annotation.CurrentMemberId;
import com.project.karrot.src.comment.CommentService;
import com.project.karrot.src.comment.dto.CommentRequestDto;
import com.project.karrot.src.interest.InterestedService;
import com.project.karrot.src.interest.dto.InterestedRequestDto;
import com.project.karrot.src.interest.dto.InterestedResponseDto;
import com.project.karrot.src.product.dto.ProductListResponseDto;
import com.project.karrot.src.product.dto.ProductResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CommentService commentService;
    private final InterestedService interestedService;

    @ApiOperation(value = "상품 상세 조회", notes = "상품 내용 상세 조회 및 등록자의 다른 상품들까지 조회한다.")
    @GetMapping("/{productId}")
    public ResponseEntity<?> viewProduct(@PathVariable Long productId, @RequestParam Long registerMemberId) {
        ProductResponseDto view = productService.findById(productId);
        List<ProductResponseDto> otherList = productService.findByMemberId(registerMemberId);
        return new ResponseEntity<>(new ProductListResponseDto(view, otherList), HttpStatus.OK);
    }

    @ApiOperation(value = "상품 좋아요", notes = "상품 좋아요 버튼을 눌러 관심 목록에 추가/삭제 한다.")
    @PutMapping("/{productId}")
    public ResponseEntity<?> like(@RequestBody InterestedRequestDto interestedRequestDto) {

        int count = 0;
        boolean like = interestedRequestDto.isLike();
        if(like) {
            InterestedResponseDto interestedResponseDto = interestedService.add(interestedRequestDto);
            count = interestedResponseDto.getProductResponseDto().getLikeCount();
        }else {
            count = interestedService.remove(interestedRequestDto);
        }

        return new ResponseEntity<>(count, HttpStatus.OK);
    }


    @ApiOperation(value = "상품 댓글 조회", notes = "상품에 등록된 댓글을 조회한다.")
    @GetMapping("/{productId}/comments")
    public ResponseEntity<?> viewComment(@PathVariable Long productId) {
        return new ResponseEntity<>(commentService.findByProductId(productId), HttpStatus.OK);
    }

    @ApiOperation(value = "상품 댓글 등록", notes = "상품에 댓글을 등록한다.")
    @PostMapping("/{productId}/comments")
    public ResponseEntity<?> registerComment(@CurrentMemberId Long memberId, @RequestBody CommentRequestDto commentRequestDto,
                                  @PathVariable Long productId) {
        return new ResponseEntity<>(commentService.register(commentRequestDto, memberId, productId), HttpStatus.OK);
    }

    @ApiOperation(value = "상품 댓글 수정", notes = "상품 댓글을 수정한다.")
    @PutMapping("/{productId}/comments")
    public ResponseEntity<?> updateComment(@CurrentMemberId Long memberId, @RequestBody CommentRequestDto commentRequestDto,
                                           @PathVariable Long productId) {
        return new ResponseEntity<>(commentService.update(commentRequestDto, memberId, productId), HttpStatus.OK);
    }

    @ApiOperation(value = "상품 댓글 삭제", notes = "상품 댓글을 삭제한다.")
    @DeleteMapping("/{productId}/comments")
    public ResponseEntity<?> deleteComment(@RequestBody CommentRequestDto commentRequestDto) {

        commentService.remove(commentRequestDto.getCommentId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
