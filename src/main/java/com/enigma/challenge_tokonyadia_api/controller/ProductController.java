package com.enigma.challenge_tokonyadia_api.controller;

import com.enigma.challenge_tokonyadia_api.entity.Product;
import com.enigma.challenge_tokonyadia_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping
    public Product createNewProduct(@RequestBody Product customer) {
        return productService.createNew(customer);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.getById(id);
    }

    @GetMapping
    public List<Product> getAllProduct() {
        return productService.getAll();
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product customer) {
        return productService.update(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable String id) {
        productService.deleteById(id);
    }
}
