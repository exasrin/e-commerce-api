package com.enigma.challenge_tokonyadia_api.controller;

import com.enigma.challenge_tokonyadia_api.dto.request.NewProductRequest;
import com.enigma.challenge_tokonyadia_api.dto.request.SearchProductRequest;
import com.enigma.challenge_tokonyadia_api.dto.request.UpdateProductRequest;
import com.enigma.challenge_tokonyadia_api.dto.response.CommonResponse;
import com.enigma.challenge_tokonyadia_api.dto.response.PagingResponse;
import com.enigma.challenge_tokonyadia_api.dto.response.ProductResponse;
import com.enigma.challenge_tokonyadia_api.service.ProductService;
import com.enigma.challenge_tokonyadia_api.util.PagingUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping()
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Long price,
            @RequestParam Integer stock,
            @RequestParam List<MultipartFile> multipartFiles
    ) {
        NewProductRequest request = NewProductRequest.builder()
                .name(name)
                .description(description)
                .price(price)
                .stock(stock)
                .multipartFiles(multipartFiles)
                .build();
        ProductResponse productResponse = productService.createNew(request);
        CommonResponse<ProductResponse> response = CommonResponse.<ProductResponse>builder()
                .message("successfully create new product")
                .statusCode(HttpStatus.CREATED.value())
                .data(productResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @GetMapping
    public ResponseEntity<?> getAllProduct(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice
    ) {
        page = PagingUtil.validatePage(page);
        size = PagingUtil.validateSize(size);
        direction = PagingUtil.validateDirection(direction);

        SearchProductRequest request = SearchProductRequest.builder()
                .page(page)
                .size(size)
                .direction(direction)
                .sortBy(sortBy)
                .name(name)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();

        Page<ProductResponse> productResponses = productService.getAll(request);
        PagingResponse pagingResponse = PagingResponse.builder()
                .count(productResponses.getTotalElements())
                .totalPages(productResponses.getTotalPages())
                .page(page)
                .size(size)
                .build();
        CommonResponse<List<ProductResponse>> response = CommonResponse.<List<ProductResponse>>builder()
                .message("successfully get all product")
                .statusCode(HttpStatus.OK.value())
                .data(productResponses.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        ProductResponse productResponse = productService.getOne(id);
        CommonResponse<ProductResponse> response = CommonResponse.<ProductResponse>builder()
                .message("successfully get product by id")
                .statusCode(HttpStatus.OK.value())
                .data(productResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody UpdateProductRequest request) {
        ProductResponse productResponse = productService.update(request);
        CommonResponse<ProductResponse> response = CommonResponse.<ProductResponse>builder()
                .message("successfully update product")
                .statusCode(HttpStatus.OK.value())
                .data(productResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable String id) {
        productService.deleteById(id);
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully delete product")
                .statusCode(HttpStatus.OK.value())
                .data("OK")
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

//    @GetMapping("/{id}/image")
//    public ResponseEntity<?> downloadProductImage(@PathVariable String id) {
//        List<Resource> resources = productService.getProductImageById(id, );
//        String headerValues = "attachment; filename=\"" + resources.getFilename() + "\"";
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
//                .body(resources);
//    }

    @GetMapping("/{id}/images/{name}")
    public ResponseEntity<?> downloadProductImage(@PathVariable String id,@PathVariable String name) {
        Resource resource = productService.getProductImageById(id,name);
        String headerValues = "attachment; filename=\"" + resource.getFilename() + "\"";
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
                .body(resource);
    }
}
