package com.enigma.challenge_tokonyadia_api.repository;

import com.enigma.challenge_tokonyadia_api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    List<Product> findAllByNameLikeIgnoreCaseOrPriceBetween(String name, Long minPrice, Long maxPrice);
}
