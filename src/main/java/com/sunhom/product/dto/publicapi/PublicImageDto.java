package com.sunhom.product.dto.publicapi;

import lombok.*;

@Getter
@Setter
@Builder
public class PublicImageDto {

    private String url; // original
    private String thumbnailUrl; // product grid
    private String mediumUrl; // product page
    private String largeUrl; // zoom

    private Boolean isPrimary;
    private String altText;
}