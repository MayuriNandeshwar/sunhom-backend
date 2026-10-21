package com.sunhom.product.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSectionRequestDto {

    @NotBlank(message = "Section code is required")
    @Size(max = 50, message = "Code must be at most 50 characters")
    @Pattern(regexp = "^[A-Z_]+$", message = "Code must contain only uppercase letters and underscores")
    private String code;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be at most 100 characters")
    private String title;

    @Size(max = 255, message = "Description must be at most 255 characters")
    private String description;

    @NotNull(message = "Display order is required")
    @Min(value = 0, message = "Display order cannot be negative")
    private Integer displayOrder;

    @NotNull(message = "Active flag is required")
    private Boolean isActive;
}
