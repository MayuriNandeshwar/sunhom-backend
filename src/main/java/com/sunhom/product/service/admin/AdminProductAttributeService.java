package com.sunhom.product.service.admin;

import org.springframework.stereotype.Service;

import com.sunhom.product.dto.ProductAttributeCreateDto;
import com.sunhom.product.entity.ProductAttribute;
import com.sunhom.product.repository.ProductAttributeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminProductAttributeService {

    private final ProductAttributeRepository attributeRepo;

    public ProductAttribute create(ProductAttributeCreateDto dto) {

        if (attributeRepo.existsByCode(dto.getCode())) {
            throw new RuntimeException("Attribute code already exists");
        }

        ProductAttribute attr = new ProductAttribute();
        attr.setCode(dto.getCode());
        attr.setName(dto.getName());
        attr.setDataType(dto.getDataType());
        attr.setIsFilterable(dto.getIsFilterable());
        attr.setIsRequired(dto.getIsRequired());
        attr.setIsActive(true);

        return attributeRepo.save(attr);
    }
}
