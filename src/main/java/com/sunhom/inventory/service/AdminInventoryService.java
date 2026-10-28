package com.sunhom.inventory.service;

import com.sunhom.inventory.dto.AdminInventoryItemDto;
import com.sunhom.inventory.dto.AdminInventorySummaryDto;
import com.sunhom.inventory.dto.AdminInventoryUpdateDto;
import com.sunhom.inventory.entity.ProductStockLog;
import com.sunhom.inventory.repository.InventoryRepository;
import com.sunhom.inventory.repository.ProductStockLogRepository;
import com.sunhom.product.entity.Inventory;
import com.sunhom.product.entity.ProductVariants;
import com.sunhom.product.entity.Products;
import com.sunhom.product.repository.ProductImageRepository;
import com.sunhom.product.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminInventoryService {

    private final InventoryRepository inventoryRepo;
    private final ProductVariantRepository variantRepo;
    private final ProductImageRepository imageRepo;
    private final ProductStockLogRepository stockLogRepo;

    @Transactional
    public List<AdminInventoryItemDto> getAllInventory() {
        List<ProductVariants> variants = variantRepo.findAll();
        List<AdminInventoryItemDto> items = new ArrayList<>();

        for (ProductVariants variant : variants) {
            if (variant.getDeletedAt() != null) {
                continue;
            }

            Products product = variant.getProducts();

            // Auto-create inventory row if missing for this variant
            Inventory inventory = inventoryRepo.findByProductVariants_VariantId(variant.getVariantId())
                    .orElseGet(() -> {
                        Inventory newInv = Inventory.builder()
                                .productVariants(variant)
                                .availableQuantity(0)
                                .reservedQuantity(0)
                                .lowStockThreshold(10)
                                .updatedAt(OffsetDateTime.now())
                                .build();
                        return inventoryRepo.save(newInv);
                    });

            String thumbnail = imageRepo.findPrimaryImageUrl(product.getProductId()).orElse(null);

            int available = inventory.getAvailableQuantity();
            int threshold = inventory.getLowStockThreshold();

            String stockStatus;
            if (available <= 0) {
                stockStatus = "OUT_OF_STOCK";
            } else if (available <= threshold) {
                stockStatus = "LOW_STOCK";
            } else {
                stockStatus = "IN_STOCK";
            }

            items.add(AdminInventoryItemDto.builder()
                    .inventoryId(inventory.getInventoryId())
                    .variantId(variant.getVariantId())
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .sku(variant.getSku())
                    .variantTitle(variant.getVariantTitle())
                    .fragrance(variant.getFragrance())
                    .color(variant.getColor())
                    .sizeLabel(variant.getSizeLabel())
                    .weightGrams(variant.getWeightGrams())
                    .price(variant.getPrice())
                    .thumbnail(thumbnail)
                    .availableQuantity(available)
                    .reservedQuantity(inventory.getReservedQuantity())
                    .lowStockThreshold(threshold)
                    .stockStatus(stockStatus)
                    .updatedAt(inventory.getUpdatedAt())
                    .build());
        }

        return items;
    }

    @Transactional
    public AdminInventorySummaryDto getInventorySummary() {
        List<AdminInventoryItemDto> all = getAllInventory();

        long totalVariants = all.size();
        long totalStockUnits = all.stream().mapToLong(AdminInventoryItemDto::getAvailableQuantity).sum();
        long inStockCount = all.stream().filter(i -> "IN_STOCK".equals(i.getStockStatus())).count();
        long lowStockCount = all.stream().filter(i -> "LOW_STOCK".equals(i.getStockStatus())).count();
        long outOfStockCount = all.stream().filter(i -> "OUT_OF_STOCK".equals(i.getStockStatus())).count();

        return AdminInventorySummaryDto.builder()
                .totalVariants(totalVariants)
                .totalStockUnits(totalStockUnits)
                .inStockCount(inStockCount)
                .lowStockCount(lowStockCount)
                .outOfStockCount(outOfStockCount)
                .build();
    }

    @Transactional
    public AdminInventoryItemDto updateStock(UUID variantId, AdminInventoryUpdateDto dto, UUID currentAdminUserId) {
        ProductVariants variant = variantRepo.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Variant not found: " + variantId));

        Inventory inventory = inventoryRepo.findByProductVariants_VariantId(variantId)
                .orElseGet(() -> Inventory.builder()
                        .productVariants(variant)
                        .availableQuantity(0)
                        .reservedQuantity(0)
                        .lowStockThreshold(10)
                        .updatedAt(OffsetDateTime.now())
                        .build());

        int oldQuantity = inventory.getAvailableQuantity();
        int newQuantity = dto.getAvailableQuantity();
        int changeAmount = newQuantity - oldQuantity;

        inventory.setAvailableQuantity(newQuantity);
        if (dto.getLowStockThreshold() != null && dto.getLowStockThreshold() >= 0) {
            inventory.setLowStockThreshold(dto.getLowStockThreshold());
        }
        inventory.setUpdatedAt(OffsetDateTime.now());
        inventory = inventoryRepo.save(inventory);

        // Record stock log for audit trail
        String changeType = changeAmount > 0 ? "RESTOCK" : (changeAmount < 0 ? "ADJUSTMENT_DECREASE" : "THRESHOLD_UPDATE");
        String note = (dto.getReason() != null && !dto.getReason().trim().isEmpty())
                ? dto.getReason().trim()
                : "Manual inventory update by admin";

        ProductStockLog log = ProductStockLog.builder()
                .variantId(variantId)
                .changeType(changeType)
                .changeAmount(changeAmount)
                .note(note)
                .changedBy(currentAdminUserId)
                .createdAt(OffsetDateTime.now())
                .build();
        stockLogRepo.save(log);

        Products product = variant.getProducts();
        String thumbnail = imageRepo.findPrimaryImageUrl(product.getProductId()).orElse(null);

        String stockStatus;
        if (newQuantity <= 0) {
            stockStatus = "OUT_OF_STOCK";
        } else if (newQuantity <= inventory.getLowStockThreshold()) {
            stockStatus = "LOW_STOCK";
        } else {
            stockStatus = "IN_STOCK";
        }

        return AdminInventoryItemDto.builder()
                .inventoryId(inventory.getInventoryId())
                .variantId(variant.getVariantId())
                .productId(product.getProductId())
                .productName(product.getProductName())
                .sku(variant.getSku())
                .variantTitle(variant.getVariantTitle())
                .fragrance(variant.getFragrance())
                .color(variant.getColor())
                .sizeLabel(variant.getSizeLabel())
                .weightGrams(variant.getWeightGrams())
                .price(variant.getPrice())
                .thumbnail(thumbnail)
                .availableQuantity(newQuantity)
                .reservedQuantity(inventory.getReservedQuantity())
                .lowStockThreshold(inventory.getLowStockThreshold())
                .stockStatus(stockStatus)
                .updatedAt(inventory.getUpdatedAt())
                .build();
    }
}
