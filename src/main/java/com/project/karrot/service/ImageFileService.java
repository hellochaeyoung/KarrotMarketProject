package com.project.karrot.service;

import com.project.karrot.domain.Member;
import com.project.karrot.domain.MemberImageFile;
import com.project.karrot.domain.Product;
import com.project.karrot.domain.ProductImageFile;
import com.project.karrot.repository.ImageFileRepository;

import java.util.List;
import java.util.Optional;

public class ImageFileService {

    private final ImageFileRepository imageFileRepository;

    public ImageFileService(ImageFileRepository imageFileRepository) {
        this.imageFileRepository = imageFileRepository;
    }

    public Long saveMemberImage(MemberImageFile file) {
        MemberImageFile saveFile = imageFileRepository.save(file);

        return saveFile.getFileId();
    }

    public Long saveProductImage(ProductImageFile file) {
        ProductImageFile saveFile = imageFileRepository.save(file);

        return saveFile.getFileId();
    }

    public Optional<MemberImageFile> findByMember(Member member) {
        return imageFileRepository.findOneByMember(member);
    }

    public List<ProductImageFile> findAllByProduct(Product product) {
        return imageFileRepository.findAllByProducts(product);
    }

    public void removeMemberImage(MemberImageFile file) {
        imageFileRepository.delete(file);
    }

    public void removeProductImage(ProductImageFile file) {
        imageFileRepository.delete(file);
    }
}
