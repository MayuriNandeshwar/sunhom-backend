package com.sunhom.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunhom.product.entity.Categories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Categories, UUID> {

    Optional<Categories> findBySlug(String slug);

    boolean existsBySlug(String slug);

    List<Categories> findByDeletedAtIsNull();

}
