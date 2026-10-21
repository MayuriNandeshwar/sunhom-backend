package com.sunhom.product.dto.admin;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminProductListDto {

    private UUID productId;
    private String name;
    private String thumbnail;
    private BigDecimal basePrice;
    private String status; // DRAFT / PUBLISHED
    private OffsetDateTime updatedAt;
}
