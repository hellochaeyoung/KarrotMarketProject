package com.project.karrot.src.member;

import com.project.karrot.src.deal.Deal;
import com.project.karrot.src.image.MemberImageFile;
import com.project.karrot.src.interest.InterestedProduct;
import com.project.karrot.src.location.Location;
import com.project.karrot.src.product.Product;
import com.project.karrot.src.auth.Salt;
import lombok.Builder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String nickName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SALT_ID")
    private Salt salt;

    @OneToMany(mappedBy = "member")
    List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    List<Deal> deals = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    List<InterestedProduct> interestedProducts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    @OneToOne(mappedBy = "member")
    private MemberImageFile file;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<InterestedProduct> getInterestedProducts() {
        return interestedProducts;
    }

    public void setInterestedProducts(List<InterestedProduct> interestedProducts) {
        this.interestedProducts = interestedProducts;
    }

    public List<Deal> getDeals() {
        return deals;
    }

    public void setDeals(List<Deal> deals) {
        this.deals = deals;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickname) {
        this.nickName = nickname;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        // 기존 지역 정보 삭제
        if(this.location != null) {
            this.location.getMembers().remove(this);
        }
        this.location = location;
        location.getMembers().add(this);
    }

    public MemberImageFile getFile() {
        return file;
    }

    public void setFile(MemberImageFile file) {
        this.file = file;
    }

    public Salt getSalt() {
        return salt;
    }

    public void setSalt(Salt salt) {
        this.salt = salt;
    }
}

