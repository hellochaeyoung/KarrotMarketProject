package com.project.karrot.service;

import com.project.karrot.domain.InterestedProduct;
import com.project.karrot.domain.Member;
import com.project.karrot.domain.Product;
import com.project.karrot.repository.InterestedRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class InterestedService {

    private final InterestedRepository interestedRepository;

    public InterestedService(InterestedRepository interestedRepository) {
        this.interestedRepository = interestedRepository;
    }

    public InterestedProduct add(InterestedProduct interestedProduct) {
        return interestedRepository.save(interestedProduct);
    }

    public Optional<InterestedProduct> find(Long interestedId) {
        return interestedRepository.findById(interestedId);
    }

    public Optional<List<InterestedProduct>> findInterestedByMember(Member member) {
        return interestedRepository.findByMember(member);
    }

    public Optional<List<InterestedProduct>> findInterestedByProduct(Product product) {
        return interestedRepository.findByProduct(product);
    }

    public void remove(InterestedProduct interestedProduct) {
        //interestedProduct.getMember().getInterestedProducts().remove(interestedProduct);
        interestedRepository.delete(interestedProduct);
    }
}
