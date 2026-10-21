package com.sunhom.product.dto.admin;

import java.util.*;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AdminProductImageDto {
    private UUID productImageId;
    private String imageUrl;
    private String altText;
    private Integer position;
    private boolean isPrimary;
}
