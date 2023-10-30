package com.enigma.challenge_tokonyadia_api.controller;

import com.enigma.challenge_tokonyadia_api.entity.Customer;
import com.enigma.challenge_tokonyadia_api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer createNewCustomer(@RequestBody Customer customer) {
        return customerService.createNew(customer);
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable String id) {
        return customerService.getById(id);
    }

    @GetMapping
    public List<Customer> getAllCustomer() {
        return customerService.getAll();
    }

    @PutMapping
    public Customer updateCustomer(@RequestBody Customer customer) {
        return customerService.update(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable String id) {
        customerService.deleteById(id);
    }
}
