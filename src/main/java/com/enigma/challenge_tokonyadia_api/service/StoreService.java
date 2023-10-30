package com.enigma.challenge_tokonyadia_api.service;

import com.enigma.challenge_tokonyadia_api.entity.Store;

import java.util.List;

public interface StoreService {
    Store createNew(Store store);
    Store getById(String id);
    List<Store> getAll();
    Store update(Store store);
    void deleteById(String id);
}
