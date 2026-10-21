package com.sunhom.product.service.admin;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import java.util.UUID;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sunhom.product.entity.ProductImages;
import com.sunhom.product.entity.Products;
import com.sunhom.product.repository.ProductImageRepository;
import com.sunhom.product.service.CloudinaryService;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminProductImageService {

    private final ProductImageRepository repo;
    private final CloudinaryService cloudinary;

    @SuppressWarnings("null")
    public void upload(UUID productId, MultipartFile file) {

        String url = cloudinary.upload(file);

        int nextPosition = repo.findMaxPosition(productId) + 1;

        ProductImages image = ProductImages.builder()
                .products(Products.withId(productId))
                .productImageUrl(url)
                .position(nextPosition)
                .isPrimary(nextPosition == 1)
                .build();

        repo.save(image);
    }

    public void reorder(UUID productId, List<UUID> ids) {

        int pos = 1;

        for (UUID id : ids) {
            repo.updatePosition(productId, id, pos++);
        }
    }

    public void delete(UUID productId, UUID imageId) {
        repo.deleteByProducts_ProductIdAndProductImageId(productId, imageId);
    }
}
