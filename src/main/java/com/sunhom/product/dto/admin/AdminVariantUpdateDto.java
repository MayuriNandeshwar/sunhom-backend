package com.sunhom.product.dto.admin;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record AdminVariantUpdateDto(

                @NotBlank String title,

                @NotNull @DecimalMin("0.0") BigDecimal price,

                @NotNull @DecimalMin("0.0") BigDecimal mrp,

                @NotNull @Min(1) Integer weightGrams,

                Boolean isDefault

) {
}
