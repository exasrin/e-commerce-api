package com.enigma.challenge_tokonyadia_api.service.impl;

import com.enigma.challenge_tokonyadia_api.dto.request.*;
import com.enigma.challenge_tokonyadia_api.dto.response.*;
import com.enigma.challenge_tokonyadia_api.entity.Customer;
import com.enigma.challenge_tokonyadia_api.repository.CustomerRepository;
import com.enigma.challenge_tokonyadia_api.service.CustomerService;
import com.enigma.challenge_tokonyadia_api.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ValidationUtil validationUtil) {
        this.customerRepository = customerRepository;
        this.validationUtil = validationUtil;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerResponse createNew(NewCustomerRequest request) {
        try {
            validationUtil.validate(request);
            Customer customer = Customer.builder()
                    .name(request.getName())
                    .address(request.getAddress())
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .build();
            customerRepository.saveAndFlush(customer);
            return mapToResponse(customer);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "phone number already exist");
        }
    }

    @Override
    public CustomerResponse createNew(Customer request) {
        Customer customer = customerRepository.saveAndFlush(request);
        return mapToResponse(customer);
    }

    @Transactional(readOnly = true)
    @Override
    public Customer getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public CustomerResponse getOne(String id) {
        Customer customer = findByIdOrThrowNotFound(id);
        return mapToResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponse> getAll(SearchCustomerRequest request) {
        for (Field declaredField : Customer.class.getDeclaredFields()) {
            if (!declaredField.getName().equals(request.getSortBy())) {
                request.setSortBy("name");
            }
        }

        Sort.Direction direction = Sort.Direction.fromString(request.getDirection());
        Pageable pageable = PageRequest.of(
                request.getPage() - 1,
                request.getSize(),
                direction,
                request.getSortBy()
        );

        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers.map(this::mapToResponse);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerResponse update(UpdateCustomerRequest request) {
        try {
            Customer currentCustomer = findByIdOrThrowNotFound(request.getId());
            currentCustomer.setId(request.getId());
            currentCustomer.setName(request.getName());
            currentCustomer.setAddress(request.getAddress());
            currentCustomer.setEmail(request.getEmail());
            currentCustomer.setPhoneNumber(request.getPhoneNumber());
            customerRepository.saveAndFlush(currentCustomer);
            return mapToResponse(currentCustomer);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "phone number already exist");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        Customer customer = findByIdOrThrowNotFound(id);
        customerRepository.delete(customer);
    }

    private Customer findByIdOrThrowNotFound(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found"));
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return CustomerResponse.builder()
                .customerId(customer.getId())
                .name(customer.getName())
                .address(customer.getAddress())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .build();
    }

}
