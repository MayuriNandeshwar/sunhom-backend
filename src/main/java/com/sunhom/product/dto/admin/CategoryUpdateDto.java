package com.sunhom.product.dto.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryUpdateDto {

    @NotBlank
    private String categoryName;

    @NotBlank
    private String slug;

    private String categoryDescription;
    private String imageUrl;
    private Boolean isActive;

    private UUID parentId;
}
