package com.project.karrot.controller;

import com.project.karrot.domain.Member;

public class StatusProductForm {

    private String status;
    private String memberId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
