package com.enigma.challenge_tokonyadia_api.service;

import com.enigma.challenge_tokonyadia_api.entity.Product;

import java.util.List;

public interface ProductService {
    Product createNew(Product product);
    Product getById(String id);
    List<Product> getAll();
    Product update(Product product);
    void deleteById(String id);
}
