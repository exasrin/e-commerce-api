package com.enigma.challenge_tokonyadia_api.controller;

import com.enigma.challenge_tokonyadia_api.entity.Store;
import com.enigma.challenge_tokonyadia_api.repository.StoreRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StoreController {
    private final StoreRepository storeRepository;

    public StoreController(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @PostMapping("/api/stores")
    public Store saveNewStore(@RequestBody Store store) {
        return storeRepository.save(store);
    }
    @GetMapping("/api/stores")
    public List<Store> findAllStore() {
        return storeRepository.findAll();
    }

    @GetMapping("/api/stores/{id}")
    public Store findStoreById(@PathVariable(name = "id") String id) {
        return storeRepository.findById(id).get();
    }

    @PutMapping("/api/stores")
    public Store updateStore(@RequestBody Store store) {
        Optional<Store> byId = storeRepository.findById(store.getId());
        if(byId.isEmpty()) {
            throw new RuntimeException();
        }
        return storeRepository.save(store);
    }

    @DeleteMapping("/api/stores/{id}")
    public void deleteStoreById(@PathVariable(name = "id") String id) {
        if(id.isEmpty()) {
            throw new RuntimeException();
        }
        storeRepository.deleteById(id);
    }
}
