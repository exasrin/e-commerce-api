package com.enigma.challenge_tokonyadia_api.service.impl;

import com.enigma.challenge_tokonyadia_api.entity.Product;
import com.enigma.challenge_tokonyadia_api.repository.ProductRepository;
import com.enigma.challenge_tokonyadia_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createNew(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product update(Product product) {
        findByIdOrThrowNotFound(product.getId());
        return productRepository.save(product);
    }

    @Override
    public void deleteById(String id) {
        Product product = findByIdOrThrowNotFound(id);
        productRepository.delete(product);
    }

    private Product findByIdOrThrowNotFound(String id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new RuntimeException("product not found"));
    }
}
