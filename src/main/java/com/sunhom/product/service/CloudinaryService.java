package com.sunhom.product.service;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String upload(MultipartFile file) {

        try {
            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    Map.of(
                            "folder", "sunhom/products",
                            "resource_type", "image"));

            return result.get("secure_url").toString();

        } catch (Exception e) {
            throw new RuntimeException("Cloudinary upload failed", e);
        }
    }
}
