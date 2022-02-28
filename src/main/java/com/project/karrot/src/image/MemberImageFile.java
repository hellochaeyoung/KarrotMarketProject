package com.project.karrot.src.image;

import com.project.karrot.src.member.Member;

import javax.persistence.*;

@Entity
@DiscriminatorValue("memberImageFile")
public class MemberImageFile extends ImageFiles {

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

}

