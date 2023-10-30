package com.enigma.challenge_tokonyadia_api.service.impl;

import com.enigma.challenge_tokonyadia_api.entity.Customer;
import com.enigma.challenge_tokonyadia_api.repository.CustomerRepository;
import com.enigma.challenge_tokonyadia_api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createNew(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer update(Customer customer) {
        findByIdOrThrowNotFound(customer.getId());
        return customerRepository.save(customer);
    }

    @Override
    public void deleteById(String id) {
        Customer customer = findByIdOrThrowNotFound(id);
        customerRepository.delete(customer);
    }

    private Customer findByIdOrThrowNotFound(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("customer not found"));
    }
}
