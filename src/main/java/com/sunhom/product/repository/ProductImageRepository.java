package com.sunhom.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sunhom.product.entity.ProductImages;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductImageRepository extends JpaRepository<ProductImages, UUID> {

    /**
     * Used in ADMIN PRODUCT LIST (thumbnail)
     */
    @Query("""
                select pi.productImageUrl
                from ProductImages pi
                where pi.products.productId = :productId
                  and pi.isPrimary = true
            """)
    Optional<String> findPrimaryImageUrl(@Param("productId") UUID productId);

    /**
     * Used in ADMIN EDIT + CUSTOMER VIEW
     * Fetch ALL images in correct display order
     */
    List<ProductImages> findByProducts_ProductIdOrderByPositionAsc(UUID productId);

    /**
     * Used during PRODUCT EDIT (replace images)
     */
    void deleteByProducts_ProductId(UUID productId);

    /**
     * Used during PUBLISH validation
     */
    long countByProducts_ProductId(UUID productId);

    @Modifying
    @Query("""
                update ProductImages pi
                set pi.position = :position
                where pi.productImageId = :imageId
                  and pi.products.productId = :productId
            """)
    void updatePosition(
            @Param("productId") UUID productId,
            @Param("imageId") UUID imageId,
            @Param("position") int position);

    @Query("""
                select coalesce(max(pi.position), 0)
                from ProductImages pi
                where pi.products.productId = :productId
            """)
    int findMaxPosition(@Param("productId") UUID productId);

    @Modifying
    void deleteByProducts_ProductIdAndProductImageId(
            UUID productId,
            UUID productImageId);

}
