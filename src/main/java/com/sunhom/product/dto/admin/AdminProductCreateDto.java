package com.sunhom.product.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.*;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminProductCreateDto {

    @NotBlank
    private String productName;

    @NotNull
    private UUID categoryId;

    private String brand;
    private String shortDescription;
    private String description;
    @NotNull
    private UUID productTypeId;
    private String seoTitle;
    private String seoDescription;
    private String seoKeywords;
}
