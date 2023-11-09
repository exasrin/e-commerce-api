package com.enigma.challenge_tokonyadia_api.controller;

import com.enigma.challenge_tokonyadia_api.dto.request.NewStoreRequest;
import com.enigma.challenge_tokonyadia_api.dto.request.SearchStoreRequest;
import com.enigma.challenge_tokonyadia_api.dto.request.UpdateStoreRequest;
import com.enigma.challenge_tokonyadia_api.dto.response.*;
import com.enigma.challenge_tokonyadia_api.dto.response.StoreResponse;
import com.enigma.challenge_tokonyadia_api.service.StoreService;
import com.enigma.challenge_tokonyadia_api.util.PagingUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@AllArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<?> createNewStore(@RequestBody NewStoreRequest request) {
        StoreResponse storeResponse = storeService.createNew(request);
        CommonResponse<StoreResponse> response = CommonResponse.<StoreResponse>builder()
                .message("successfully create new store")
                .statusCode(HttpStatus.CREATED.value())
                .data(storeResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStoreById(@PathVariable String id) {
        StoreResponse storeResponse = storeService.getOne(id);
        CommonResponse<StoreResponse> response = CommonResponse.<StoreResponse>builder()
                .message("successfully get store by id")
                .statusCode(HttpStatus.OK.value())
                .data(storeResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllStore(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false, defaultValue = "name") String sortBy
    ) {
        page = PagingUtil.validatePage(page);
        size = PagingUtil.validateSize(size);
        direction = PagingUtil.validateDirection(direction);

        SearchStoreRequest request = SearchStoreRequest.builder()
                .page(page)
                .size(size)
                .direction(direction)
                .sortBy(sortBy)
                .build();
        Page<StoreResponse> stores = storeService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .count(stores.getTotalElements())
                .totalPages(stores.getTotalPages())
                .build();

        CommonResponse<List<StoreResponse>> response = CommonResponse.<List<StoreResponse>>builder()
                .message("successfully get all store")
                .statusCode(HttpStatus.OK.value())
                .data(stores.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateStore(@RequestBody UpdateStoreRequest request) {
        StoreResponse storeResponse = storeService.update(request);
        CommonResponse<StoreResponse> response = CommonResponse.<StoreResponse>builder()
                .message("successfully update store")
                .statusCode(HttpStatus.OK.value())
                .data(storeResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStoreById(@PathVariable String id) {
        storeService.deleteById(id);
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully update store")
                .statusCode(HttpStatus.OK.value())
                .data("OK")
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
