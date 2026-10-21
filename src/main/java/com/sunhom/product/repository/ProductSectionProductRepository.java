package com.sunhom.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunhom.product.entity.ProductSectionProduct;

import java.util.List;
import java.util.UUID;

public interface ProductSectionProductRepository
        extends JpaRepository<ProductSectionProduct, UUID> {

    List<ProductSectionProduct> findBySection_SectionIdAndIsActiveTrueOrderByDisplayOrderAsc(UUID sectionId);

    boolean existsBySection_SectionIdAndProducts_ProductId(UUID sectionId, UUID productId);

    void deleteBySection_SectionId(UUID sectionId);
}
