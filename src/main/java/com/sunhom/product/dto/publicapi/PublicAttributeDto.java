package com.sunhom.product.dto.publicapi;

import lombok.*;

@Getter
@Setter
@Builder
public class PublicAttributeDto {

    private String code;
    private String label;
    private String type;
    private Object value;
}
