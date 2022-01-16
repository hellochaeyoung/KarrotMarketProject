package com.project.karrot.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    private String contents;
    private String time;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        if(product == null) { // 연관관계 삭제
            this.product.getComments().remove(this);
        }else {
            if(this.product != null) {
                this.product.getComments().remove(this);
            }
            this.product = product;
            product.getComments().add(this);
        }
    }
}
