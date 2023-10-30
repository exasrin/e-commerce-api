package com.enigma.challenge_tokonyadia_api.service;

import com.enigma.challenge_tokonyadia_api.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer createNew(Customer customer);
    Customer getById(String id);
    List<Customer> getAll();
    Customer update(Customer customer);
    void deleteById(String id);
}
