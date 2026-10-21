package com.sunhom.product.dto.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeUpdateDto {

    @NotBlank
    private String productTypeName;

    private String productTypeDescription;
}
