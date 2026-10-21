package com.sunhom.product.repository.publicapi;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunhom.product.entity.ProductEntityAttribute;

import java.util.List;
import java.util.UUID;

public interface PublicAttributeRepository
        extends JpaRepository<ProductEntityAttribute, UUID> {

    List<ProductEntityAttribute> findByProducts_ProductId(UUID productId);
}
