package com.enigma.challenge_tokonyadia_api.service.impl;

import com.enigma.challenge_tokonyadia_api.entity.Store;
import com.enigma.challenge_tokonyadia_api.repository.StoreRepository;
import com.enigma.challenge_tokonyadia_api.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;

    @Autowired
    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public Store createNew(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public Store getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public List<Store> getAll() {
        return storeRepository.findAll();
    }

    @Override
    public Store update(Store store) {
        findByIdOrThrowNotFound(store.getId());
        return storeRepository.save(store);
    }

    @Override
    public void deleteById(String id) {
        Store store = findByIdOrThrowNotFound(id);
        storeRepository.delete(store);
    }

    private Store findByIdOrThrowNotFound(String id) {
        Optional<Store> store = storeRepository.findById(id);
        return store.orElseThrow(() -> new RuntimeException("store not found"));
    }
}
