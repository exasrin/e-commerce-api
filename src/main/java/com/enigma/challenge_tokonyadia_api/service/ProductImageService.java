package com.enigma.challenge_tokonyadia_api.service;

import com.enigma.challenge_tokonyadia_api.entity.ProductImage;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageService {
    List<ProductImage> createFile(List<MultipartFile> multipartFile);
    Resource findByPath(String path);
    void deleteFile(ProductImage productImage);
}
