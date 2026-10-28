package com.sunhom.inventory.repository;

import com.sunhom.inventory.entity.ProductStockLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductStockLogRepository extends JpaRepository<ProductStockLog, UUID> {
    List<ProductStockLog> findByVariantIdOrderByCreatedAtDesc(UUID variantId);
}
