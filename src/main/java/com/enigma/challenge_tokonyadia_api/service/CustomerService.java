package com.enigma.challenge_tokonyadia_api.service;

import com.enigma.challenge_tokonyadia_api.dto.request.NewCustomerRequest;
import com.enigma.challenge_tokonyadia_api.dto.request.SearchCustomerRequest;
import com.enigma.challenge_tokonyadia_api.dto.request.UpdateCustomerRequest;
import com.enigma.challenge_tokonyadia_api.dto.response.CustomerResponse;
import com.enigma.challenge_tokonyadia_api.entity.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    CustomerResponse createNew(NewCustomerRequest request);
    Customer getById(String id);
    CustomerResponse getOne(String id);
    Page<CustomerResponse> getAll(SearchCustomerRequest request);
    CustomerResponse update(UpdateCustomerRequest request);
    void deleteById(String id);
}
