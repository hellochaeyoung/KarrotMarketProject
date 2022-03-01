package com.project.karrot.src.interest;

import com.project.karrot.src.interest.InterestedProduct;
import com.project.karrot.src.member.Member;
import com.project.karrot.src.product.Product;
import com.project.karrot.src.interest.InterestedRepository;

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
        return interestedRepository.findByMember(member.getId());
    }

    public Optional<List<InterestedProduct>> findInterestedByProduct(Product product) {
        return interestedRepository.findByProduct(product.getProductId());
    }

    public void remove(InterestedProduct interestedProduct) {
        //interestedProduct.getMember().getInterestedProducts().remove(interestedProduct);
        interestedRepository.delete(interestedProduct);
    }
}
