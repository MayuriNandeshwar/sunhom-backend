package com.sunhom.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class ProductAttributeAssignDto {

    @NotBlank
    private String attributeCode;

    private String valueText;
    private Double valueNumber;
    private Boolean valueBoolean;
    private String valueEnum;
}
