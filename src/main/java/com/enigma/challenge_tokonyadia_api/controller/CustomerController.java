package com.enigma.challenge_tokonyadia_api.controller;

import com.enigma.challenge_tokonyadia_api.dto.request.NewCustomerRequest;
import com.enigma.challenge_tokonyadia_api.dto.request.SearchCustomerRequest;
import com.enigma.challenge_tokonyadia_api.dto.request.UpdateCustomerRequest;
import com.enigma.challenge_tokonyadia_api.dto.response.CommonResponse;
import com.enigma.challenge_tokonyadia_api.dto.response.CustomerResponse;
import com.enigma.challenge_tokonyadia_api.dto.response.PagingResponse;
import com.enigma.challenge_tokonyadia_api.service.CustomerService;
import com.enigma.challenge_tokonyadia_api.util.PagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> createNewCustomer(@RequestBody NewCustomerRequest request) {
        CustomerResponse customerResponse = customerService.createNew(request);
        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .message("successfully create new customer")
                .statusCode(HttpStatus.CREATED.value())
                .data(customerResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        CustomerResponse customerResponse = customerService.getOne(id);
        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .message("successfully get customer by id")
                .statusCode(HttpStatus.OK.value())
                .data(customerResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomer(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false, defaultValue = "name") String sortBy
    ) {
        page = PagingUtil.validatePage(page);
        size = PagingUtil.validateSize(size);
        direction = PagingUtil.validateDirection(direction);

        SearchCustomerRequest request = SearchCustomerRequest.builder()
                .page(page)
                .size(size)
                .direction(direction)
                .sortBy(sortBy)
                .build();
        Page<CustomerResponse> customers = customerService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .count(customers.getTotalElements())
                .totalPages(customers.getTotalPages())
                .build();

        CommonResponse<List<CustomerResponse>> response = CommonResponse.<List<CustomerResponse>>builder()
                .message("successfully get all customer")
                .statusCode(HttpStatus.OK.value())
                .data(customers.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody UpdateCustomerRequest request) {
        CustomerResponse customerResponse = customerService.update(request);
        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .message("successfully update customer")
                .statusCode(HttpStatus.OK.value())
                .data(customerResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable String id) {
        customerService.deleteById(id);
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully update customer")
                .statusCode(HttpStatus.OK.value())
                .data("OK")
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
