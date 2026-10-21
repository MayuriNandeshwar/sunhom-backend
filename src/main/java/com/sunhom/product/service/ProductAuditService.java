package com.sunhom.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.sunhom.product.entity.ProductAuditLog;
import com.sunhom.product.repository.ProductAuditRepository;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductAuditService {

    private final ProductAuditRepository repo;

    public void log(UUID productId, String action, UUID userId) {

        ProductAuditLog log = ProductAuditLog.builder()
                .id(UUID.randomUUID())
                .productId(productId)
                .action(action)
                .performedBy(userId)
                .createdAt(OffsetDateTime.now())
                .build();

        repo.save(log);
    }
}
