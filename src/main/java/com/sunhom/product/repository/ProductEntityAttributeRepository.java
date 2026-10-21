package com.sunhom.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunhom.product.entity.ProductEntityAttribute;

import java.util.List;
import java.util.UUID;

public interface ProductEntityAttributeRepository
        extends JpaRepository<ProductEntityAttribute, UUID> {

    List<ProductEntityAttribute> findByProducts_ProductId(UUID productId);

    List<ProductEntityAttribute> findByVariant_VariantId(UUID variantId);

    void deleteByProducts_ProductId(UUID productId);

    void deleteByVariant_VariantId(UUID variantId);
}
