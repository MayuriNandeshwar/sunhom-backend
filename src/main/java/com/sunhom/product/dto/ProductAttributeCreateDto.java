package com.sunhom.product.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class ProductAttributeCreateDto {

    @NotBlank
    @Pattern(regexp = "^[A-Z_]+$")
    private String code;

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "TEXT|NUMBER|BOOLEAN|ENUM")
    private String dataType;

    private Boolean isFilterable = false;
    private Boolean isRequired = false;
}
