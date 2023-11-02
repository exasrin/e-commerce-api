package com.enigma.challenge_tokonyadia_api.service.impl;

import com.enigma.challenge_tokonyadia_api.dto.request.NewProductRequest;
import com.enigma.challenge_tokonyadia_api.dto.request.SearchProductRequest;
import com.enigma.challenge_tokonyadia_api.dto.request.UpdateProductRequest;
import com.enigma.challenge_tokonyadia_api.dto.response.ProductResponse;
import com.enigma.challenge_tokonyadia_api.entity.Product;
import com.enigma.challenge_tokonyadia_api.repository.ProductRepository;
import com.enigma.challenge_tokonyadia_api.service.ProductService;
import com.enigma.challenge_tokonyadia_api.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ValidationUtil validationUtil;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ValidationUtil validationUtil) {
        this.productRepository = productRepository;
        this.validationUtil = validationUtil;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductResponse createNew(NewProductRequest request) {
        validationUtil.validate(request);
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();
        productRepository.saveAndFlush(product);
        return mapToResponse(product);
    }


    @Transactional(readOnly = true)
    @Override
    public Product getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductResponse getOne(String id) {
        Product product = findByIdOrThrowNotFound(id);
        return mapToResponse(product);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductResponse> getAll(SearchProductRequest request) {
        Specification<Product> specification = getProductSpecification(request);
        Sort.Direction direction = Sort.Direction.fromString(request.getDirection());
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), direction, request.getSortBy());
        Page<Product> products = productRepository.findAll(specification, pageable);
        return products.map(this::mapToResponse);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductResponse update(UpdateProductRequest request) {
        Product product = findByIdOrThrowNotFound(request.getId());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        productRepository.saveAndFlush(product);
        return mapToResponse(product);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        Product product = findByIdOrThrowNotFound(id);
        productRepository.delete(product);
    }

    private Product findByIdOrThrowNotFound(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
    }

    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

    private Specification<Product> getProductSpecification(SearchProductRequest request) {
        return  (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getName() != null) {
                Predicate name = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + request.getName().toLowerCase() + "%"
                );
                predicates.add(name);
            }

            if (request.getMinPrice() != null) {
                Predicate price = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), request.getMinPrice());
                predicates.add(price);
            }

            if (request.getMaxPrice() != null) {
                Predicate price = criteriaBuilder.lessThanOrEqualTo(root.get("price"), request.getMaxPrice());
                predicates.add(price);
            }

            return query
                    .where(predicates.toArray(new Predicate[]{}))
                    .getRestriction();
        };
    }
}
