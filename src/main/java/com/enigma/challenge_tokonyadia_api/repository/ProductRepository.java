package com.enigma.challenge_tokonyadia_api.repository;

import com.enigma.challenge_tokonyadia_api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
