package com.sunhom.product.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSectionResponseDto {

    private UUID sectionId;
    private String code;
    private String title;
    private String description;

    private Integer displayOrder;
    private Boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
