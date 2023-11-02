package com.enigma.challenge_tokonyadia_api.service;

import com.enigma.challenge_tokonyadia_api.dto.request.NewProductRequest;
import com.enigma.challenge_tokonyadia_api.dto.request.SearchProductRequest;
import com.enigma.challenge_tokonyadia_api.dto.request.UpdateProductRequest;
import com.enigma.challenge_tokonyadia_api.dto.response.ProductResponse;
import com.enigma.challenge_tokonyadia_api.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductService {
    ProductResponse createNew(NewProductRequest request);
    Product getById(String id);
    ProductResponse getOne(String id);
    Page<ProductResponse> getAll(SearchProductRequest request);
    ProductResponse update(UpdateProductRequest request);
    void deleteById(String id);
}
