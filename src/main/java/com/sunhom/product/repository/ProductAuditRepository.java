package com.sunhom.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunhom.product.entity.ProductAuditLog;

import java.util.List;
import java.util.UUID;

public interface ProductAuditRepository
        extends JpaRepository<ProductAuditLog, UUID> {

    List<ProductAuditLog> findByProductIdOrderByCreatedAtDesc(UUID productId);
}
