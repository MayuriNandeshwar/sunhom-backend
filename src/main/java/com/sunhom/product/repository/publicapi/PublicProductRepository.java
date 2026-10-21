package com.sunhom.product.repository.publicapi;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunhom.product.entity.Products;

import java.util.Optional;
import java.util.UUID;

public interface PublicProductRepository
        extends JpaRepository<Products, UUID> {

    Optional<Products> findBySlugAndIsActiveTrue(String slug);
}
