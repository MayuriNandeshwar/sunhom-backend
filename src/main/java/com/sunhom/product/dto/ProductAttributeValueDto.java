package com.sunhom.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class ProductAttributeValueDto {

    @NotBlank
    private String value;

    private Integer displayOrder = 0;
}
