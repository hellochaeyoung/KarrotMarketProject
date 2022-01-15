package com.project.karrot.service;

import com.project.karrot.domain.InterestedProduct;
import com.project.karrot.domain.Member;
import com.project.karrot.domain.Product;
import com.project.karrot.repository.InterestedRepository;

import java.util.List;
import java.util.Optional;

public class InterestedService {

    private final Member member;
    private final InterestedRepository interestedRepository;

    public InterestedService(Member member, InterestedRepository interestedRepository) {
        this.member = member;
        this.interestedRepository = interestedRepository;
    }

    public InterestedProduct add(Product product) {

        InterestedProduct interestedProduct = new InterestedProduct();
        interestedProduct.setMember(member);
        interestedProduct.setProduct(product);

        return interestedRepository.save(interestedProduct);
    }

    public Optional<InterestedProduct> find(Long interestedId) {
        return interestedRepository.findById(interestedId);
    }

    public List<InterestedProduct> findInterestedByMember(Member member) {
        return interestedRepository.findByMember(member);
    }

    public List<InterestedProduct> findInterestedByProduct(Product product) {
        return interestedRepository.findByProduct(product);
    }

    public void remove(InterestedProduct interestedProduct) {
        //interestedProduct.getMember().getInterestedProducts().remove(interestedProduct);
        interestedRepository.delete(interestedProduct);
    }
}
