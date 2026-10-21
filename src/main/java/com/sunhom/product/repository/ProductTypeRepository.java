package com.sunhom.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunhom.product.entity.ProductTypes;

import java.util.Optional;
import java.util.UUID;

public interface ProductTypeRepository extends JpaRepository<ProductTypes, UUID> {

    boolean existsByProductTypeName(String name);

    Optional<ProductTypes> findByProductTypeName(String name);
}
