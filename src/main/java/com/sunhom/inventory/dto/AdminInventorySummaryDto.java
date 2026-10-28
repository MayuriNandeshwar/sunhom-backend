package com.sunhom.inventory.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminInventorySummaryDto {
    private long totalVariants;
    private long totalStockUnits;
    private long inStockCount;
    private long lowStockCount;
    private long outOfStockCount;
}
