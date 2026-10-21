package com.sunhom.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sunhom.product.entity.Products;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Products, UUID> {

    // =========================
    // BASIC PRODUCT QUERIES
    // =========================

    Optional<Products> findBySlug(String slug);

    boolean existsBySlug(String slug);

    // =========================
    // AUTO-BESTSELLER (NATIVE SQL)
    // =========================

    @Modifying
    @Transactional
    @Query(value = """
                UPDATE products
                SET is_auto_bestseller = false
                WHERE is_auto_bestseller = true
            """, nativeQuery = true)
    void clearAutoBestsellers();

    @Modifying
    @Transactional
    @Query(value = """
                UPDATE products
                SET is_auto_bestseller = true
                WHERE product_id IN (:productIds)
            """, nativeQuery = true)
    void markAsAutoBestseller(@Param("productIds") List<UUID> productIds);
}
