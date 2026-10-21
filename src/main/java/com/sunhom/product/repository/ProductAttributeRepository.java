package com.sunhom.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunhom.product.entity.ProductAttribute;

import java.util.Optional;
import java.util.UUID;

public interface ProductAttributeRepository
        extends JpaRepository<ProductAttribute, UUID> {

    Optional<ProductAttribute> findByCode(String code);

    boolean existsByCode(String code);
}
