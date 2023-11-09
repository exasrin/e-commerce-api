package com.enigma.challenge_tokonyadia_api.service.impl;

import com.enigma.challenge_tokonyadia_api.entity.ProductImage;
import com.enigma.challenge_tokonyadia_api.repository.ProductImageRepository;
import com.enigma.challenge_tokonyadia_api.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final List<String> contentTypes = List.of("image/jpg", "image/png");
    private final Path directoryPath;

    @Autowired
    public ProductImageServiceImpl(ProductImageRepository productImageRepository, @Value("${app.tokonyadia.directory-image-path}") String directoryPath) {
        this.productImageRepository = productImageRepository;
        this.directoryPath = Paths.get(directoryPath);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ProductImage> createFile(List<MultipartFile> multipartFile) {
        try {
            if(multipartFile.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is required");

            if(!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file : multipartFile) {
//                saveValidation(file);

                String filename = String.format("%d_%s", System.currentTimeMillis(), file.getOriginalFilename());
                Path filePath = directoryPath.resolve(filename);
                Files.copy(file.getInputStream(), filePath);

                ProductImage productImage = ProductImage.builder()
                        .name(filename)
                        .contentType(file.getContentType())
                        .size(file.getSize())
                        .path(filePath.toString())
                        .build();
                productImages.add(productImage);
            }
            return productImageRepository.saveAllAndFlush(productImages);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Resource findByPath(String path) {
        try {
            Path filePath = Paths.get(path);
            if(!Files.exists(filePath)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "image not found");
            }
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(ProductImage productImage) {
        try {
            Path filePath = Paths.get(productImage.getPath());
            if(!Files.exists(filePath)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "image not found");
            }
            Files.delete(filePath);
            productImageRepository.delete(productImage);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void saveValidation(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "image is required");
        if (!contentTypes.contains(multipartFile.getContentType()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid content type");
        if (!Files.exists(directoryPath)) {
            Files.createDirectory(directoryPath);
        }
    }
}
