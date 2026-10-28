package com.sunhom.inventory.controller;

import com.sunhom.inventory.dto.AdminInventoryItemDto;
import com.sunhom.inventory.dto.AdminInventorySummaryDto;
import com.sunhom.inventory.dto.AdminInventoryUpdateDto;
import com.sunhom.inventory.service.AdminInventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/inventory")
@RequiredArgsConstructor
public class AdminInventoryController {

    private final AdminInventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<AdminInventoryItemDto>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @GetMapping("/summary")
    public ResponseEntity<AdminInventorySummaryDto> getSummary() {
        return ResponseEntity.ok(inventoryService.getInventorySummary());
    }

    @PutMapping("/{variantId}")
    public ResponseEntity<AdminInventoryItemDto> updateStock(
            @PathVariable UUID variantId,
            @RequestBody @Valid AdminInventoryUpdateDto dto,
            Authentication authentication) {

        UUID adminId = null;
        if (authentication != null && authentication.getPrincipal() instanceof UUID) {
            adminId = (UUID) authentication.getPrincipal();
        }

        return ResponseEntity.ok(inventoryService.updateStock(variantId, dto, adminId));
    }
}
