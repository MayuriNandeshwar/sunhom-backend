package com.sunhom.product.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunhom.product.dto.admin.AdminVariantCreateDto;
import com.sunhom.product.entity.ProductVariants;
import com.sunhom.product.entity.Products;
import com.sunhom.product.repository.ProductRepository;
import com.sunhom.product.repository.ProductVariantRepository;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminVariantService {

    private final ProductRepository productRepo;
    private final ProductVariantRepository variantRepo;

    @Transactional
    public void addVariant(UUID productId, AdminVariantCreateDto dto) {

        Products product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductVariants variant = ProductVariants.builder()
                .products(product)
                .sku(dto.getSku())
                .price(dto.getPrice())
                .mrp(dto.getMrp())
                .variantTitle(dto.getVariantTitle())
                .weightGrams(dto.getWeightGrams())
                .isDefault(dto.isDefault())
                .isActive(true)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        variantRepo.save(variant);
    }
}
