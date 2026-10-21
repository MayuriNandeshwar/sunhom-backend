package com.sunhom.product.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunhom.product.entity.Products;
import com.sunhom.product.repository.*;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminProductPublishService {

    private final ProductRepository productRepo;
    private final ProductVariantRepository variantRepo;
    private final ProductImageRepository imageRepo;

    @Transactional
    public void publish(UUID productId) {

        Products product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (variantRepo.countByProducts_ProductIdAndIsActiveTrue(productId) == 0) {
            throw new RuntimeException("At least one active variant required");
        }

        if (variantRepo.countByProducts_ProductIdAndIsDefaultTrue(productId) == 0) {
            throw new RuntimeException("Default variant required");
        }

        if (imageRepo.countByProducts_ProductId(productId) == 0) {
            throw new RuntimeException("At least one image required");
        }

        product.setActive(true);
        productRepo.save(product);
    }
}
