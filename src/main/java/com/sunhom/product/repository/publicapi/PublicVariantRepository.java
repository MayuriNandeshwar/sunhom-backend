package com.sunhom.product.repository.publicapi;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunhom.product.entity.ProductVariants;

import java.util.List;
import java.util.UUID;

public interface PublicVariantRepository
        extends JpaRepository<ProductVariants, UUID> {

    List<ProductVariants> findByProducts_ProductIdAndIsActiveTrue(UUID productId);
}
