package com.sunhom.product.dto.admin;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDto {

    private UUID categoryId;
    private String categoryName;
    private String slug;
    private String categoryDescription;
    private String imageUrl;
    private boolean isActive;
    private UUID parentId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
