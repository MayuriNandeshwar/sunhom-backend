package com.sunhom.product.dto.admin;

import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductTypeResponseDto {

    private UUID productTypeId;
    private String productTypeName;
    private String productTypeDescription;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
