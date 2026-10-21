package com.sunhom.product.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionProductResponseDto {

    private UUID id;
    private UUID sectionId;
    private UUID productId;
    private Integer displayOrder;
    private Boolean isActive;
}
