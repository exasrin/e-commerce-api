package com.enigma.challenge_tokonyadia_api.controller;

import com.enigma.challenge_tokonyadia_api.entity.Customer;
import com.enigma.challenge_tokonyadia_api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping("/api/customers")
    public Customer saveNewCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }
    @GetMapping("/api/customers")
    public List<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }

    @GetMapping("/api/customers/{id}")
    public Customer findCustomerById(@PathVariable(name = "id") String id) {
        return customerRepository.findById(id).get();
    }

    @PutMapping("/api/customers")
    public Customer updateCustomer(@RequestBody Customer customer) {
        Optional<Customer> byId = customerRepository.findById(customer.getId());
        if(byId.isEmpty()) {
            throw new RuntimeException();
        }
        return customerRepository.save(customer);
    }

    @DeleteMapping("/api/customers/{id}")
    public void deleteCustomerById(@PathVariable(name = "id") String id) {
        if(id.isEmpty()) {
            throw new RuntimeException();
        }
        customerRepository.deleteById(id);
    }
}
