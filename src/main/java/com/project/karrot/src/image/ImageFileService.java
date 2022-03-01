package com.project.karrot.src.image;

import com.project.karrot.src.member.Member;
import com.project.karrot.src.image.MemberImageFile;
import com.project.karrot.src.product.Product;
import com.project.karrot.src.image.ProductImageFile;
import com.project.karrot.src.image.ImageFileRepository;

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
        return imageFileRepository.findOneByMember(member.getId());
    }

    public List<ProductImageFile> findAllByProduct(Product product) {
        return imageFileRepository.findAllByProduct(product.getProductId());
    }

    public void removeMemberImage(MemberImageFile file) {
        imageFileRepository.delete(file);
    }

    public void removeProductImage(ProductImageFile file) {
        imageFileRepository.delete(file);
    }
}
