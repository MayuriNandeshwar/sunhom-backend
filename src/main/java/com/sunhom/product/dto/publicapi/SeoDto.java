package com.sunhom.product.dto.publicapi;

import lombok.*;

@Getter
@Setter
@Builder
public class SeoDto {

    private String title;
    private String description;
    private String keywords;
}
