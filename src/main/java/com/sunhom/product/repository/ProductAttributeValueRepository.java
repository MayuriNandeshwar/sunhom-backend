package com.sunhom.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunhom.product.entity.ProductAttributeValue;

import java.util.List;
import java.util.UUID;

public interface ProductAttributeValueRepository
        extends JpaRepository<ProductAttributeValue, UUID> {

    List<ProductAttributeValue> findByAttribute_AttributeIdAndIsActiveTrueOrderByDisplayOrderAsc(
            UUID attributeId);
}
