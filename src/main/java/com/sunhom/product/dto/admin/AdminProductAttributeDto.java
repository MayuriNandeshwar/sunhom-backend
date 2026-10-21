package com.sunhom.product.dto.admin;

import java.math.BigDecimal;
import java.util.*;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AdminProductAttributeDto {
    private UUID attributeId;

    private String valueText;
    private BigDecimal valueNumber;
    private Boolean valueBoolean;
    private UUID enumValueId;
}
