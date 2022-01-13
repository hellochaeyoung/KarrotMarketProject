package com.project.karrot.domain;

import javax.persistence.*;

@Entity
public class MemberImageFile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fileId;

    private String fileName;
    private String fileOriName;
    private String fileURL;

    @OneToOne(mappedBy = "file")
    private Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileOriName() {
        return fileOriName;
    }

    public void setFileOriName(String fileOriName) {
        this.fileOriName = fileOriName;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }
}

