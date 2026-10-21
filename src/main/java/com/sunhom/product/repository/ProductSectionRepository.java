package com.sunhom.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunhom.product.entity.ProductSection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductSectionRepository extends JpaRepository<ProductSection, UUID> {

    Optional<ProductSection> findByCode(String code);

    boolean existsByCode(String code);

    List<ProductSection> findByIsActiveTrueOrderByDisplayOrderAsc();
}
