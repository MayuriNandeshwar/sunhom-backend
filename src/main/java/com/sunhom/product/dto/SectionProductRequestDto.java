package com.sunhom.product.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionProductRequestDto {

    @NotNull(message = "Section ID is required")
    private UUID sectionId;

    @NotNull(message = "Product ID is required")
    private UUID productId;

    @NotNull(message = "Display order is required")
    @Min(0)
    private Integer displayOrder;

    @NotNull
    private Boolean isActive;
}
