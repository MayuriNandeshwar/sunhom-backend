package com.sunhom.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sunhom.product.entity.ProductVariants;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductVariantRepository
        extends JpaRepository<ProductVariants, UUID> {

    long countByProducts_ProductIdAndIsActiveTrue(UUID productId);

    long countByProducts_ProductIdAndIsDefaultTrue(UUID productId);

    @Query("""
                select v.price
                from ProductVariants v
                where v.products.productId = :productId
                  and v.isDefault = true
                  and v.isActive = true
            """)
    Optional<BigDecimal> findDefaultVariantPrice(@Param("productId") UUID productId);

    List<ProductVariants> findByProducts_ProductId(UUID productId);
}
