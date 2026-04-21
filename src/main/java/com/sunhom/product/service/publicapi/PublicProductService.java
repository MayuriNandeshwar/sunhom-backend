package com.sunhom.product.service.publicapi;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunhom.product.dto.publicapi.*;
import com.sunhom.product.entity.*;
import com.sunhom.product.repository.*;
import com.sunhom.product.repository.publicapi.PublicAttributeRepository;
import com.sunhom.product.repository.publicapi.PublicProductRepository;
import com.sunhom.product.repository.publicapi.PublicVariantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicProductService {

        private final PublicProductRepository productRepo;
        private final PublicVariantRepository variantRepo;
        private final PublicAttributeRepository attributeRepo;
        private final ProductImageRepository imageRepo;

        @Transactional(readOnly = true)
        // @Cacheable(value = "publicProduct", key = "#slug")
        public PublicProductDetailDto getProductBySlug(String slug) {

                Products product = productRepo
                                .findBySlugAndIsActiveTrue(slug)
                                .orElseThrow(() -> new RuntimeException("Product not found"));

                List<ProductVariants> variants = variantRepo
                                .findByProducts_ProductIdAndIsActiveTrue(product.getProductId());

                List<ProductEntityAttribute> attributes = attributeRepo
                                .findByProducts_ProductId(product.getProductId());

                List<ProductImages> images = imageRepo
                                .findByProducts_ProductIdOrderByPositionAsc(product.getProductId());

                return PublicProductDetailDto.builder()
                                .productId(product.getProductId())
                                .name(product.getProductName())
                                .slug(product.getSlug())
                                .brand(product.getBrand())
                                .description(product.getProductDescription())

                                // ✅ CATEGORY ADDED
                                .category(product.getCategories().getCategoryName())
                                .categorySlug(product.getCategories().getSlug())

                                .seo(mapSeo(product))
                                .variants(mapVariants(variants))
                                .attributes(mapAttributes(attributes))
                                .images(mapImages(images))
                                .build();
        }

        private SeoDto mapSeo(Products p) {
                return SeoDto.builder()
                                .title(p.getSeoTitle())
                                .description(p.getSeoDescription())
                                .keywords(p.getSeoKeywords())
                                .build();
        }

        private List<PublicVariantDto> mapVariants(List<ProductVariants> variants) {

                return variants.stream()
                                .map(v -> PublicVariantDto.builder()
                                                .variantId(v.getVariantId())
                                                .title(v.getVariantTitle())
                                                .sku(v.getSku())
                                                .price(v.getPrice().doubleValue())
                                                .mrp(v.getMrp() != null ? v.getMrp().doubleValue() : null)
                                                .isDefault(v.isDefault())
                                                .weightGrams(v.getWeightGrams())
                                                .build())
                                .toList();
        }

        private List<PublicAttributeDto> mapAttributes(List<ProductEntityAttribute> attrs) {

                return attrs.stream()
                                .map(a -> PublicAttributeDto.builder()
                                                .code(a.getAttribute().getCode())
                                                .label(a.getAttribute().getName())
                                                .type(a.getAttribute().getDataType())
                                                .value(resolveValue(a))
                                                .build())
                                .toList();
        }

        private Object resolveValue(ProductEntityAttribute a) {

                return switch (a.getAttribute().getDataType()) {
                        case "TEXT" -> a.getValueText();
                        case "NUMBER" -> a.getValueNumber();
                        case "BOOLEAN" -> a.getValueBoolean();
                        case "ENUM" -> a.getEnumValue().getValue();
                        default -> null;
                };
        }

        private List<PublicImageDto> mapImages(List<ProductImages> images) {

                return images.stream()
                                .map(i -> PublicImageDto.builder()
                                                .url(i.getProductImageUrl())
                                                .thumbnailUrl(i.getUrlThumbnail())
                                                .mediumUrl(i.getUrlMedium())
                                                .largeUrl(i.getUrlLarge())
                                                .isPrimary(i.isPrimary())
                                                .altText(i.getAltText())
                                                .build())
                                .toList();
        }
}