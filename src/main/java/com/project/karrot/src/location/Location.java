package com.project.karrot.src.location;

import com.project.karrot.src.member.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Location<Product> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long locationId;

    private String address;

    @OneToMany(mappedBy = "location")
    List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "location")
    List<Member> members = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

