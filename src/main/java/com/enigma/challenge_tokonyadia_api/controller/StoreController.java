package com.enigma.challenge_tokonyadia_api.controller;

import com.enigma.challenge_tokonyadia_api.entity.Store;
import com.enigma.challenge_tokonyadia_api.entity.Store;
import com.enigma.challenge_tokonyadia_api.repository.StoreRepository;
import com.enigma.challenge_tokonyadia_api.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stores")
public class StoreController {
    private final StoreService storeService;
    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public Store createNewStore(@RequestBody Store store) {
        return storeService.createNew(store);
    }

    @GetMapping("/{id}")
    public Store getStoreById(@PathVariable String id) {
        return storeService.getById(id);
    }

    @GetMapping
    public List<Store> getAllStore() {
        return storeService.getAll();
    }

    @PutMapping
    public Store updateStore(@RequestBody Store store) {
        return storeService.update(store);
    }

    @DeleteMapping("/{id}")
    public void deleteStoreById(@PathVariable String id) {
        storeService.deleteById(id);
    }
}
