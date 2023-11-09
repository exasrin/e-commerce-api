package com.enigma.challenge_tokonyadia_api.service;

import com.enigma.challenge_tokonyadia_api.dto.request.NewStoreRequest;
import com.enigma.challenge_tokonyadia_api.dto.request.SearchStoreRequest;
import com.enigma.challenge_tokonyadia_api.dto.request.UpdateStoreRequest;
import com.enigma.challenge_tokonyadia_api.dto.response.ProductResponse;
import com.enigma.challenge_tokonyadia_api.dto.response.StoreResponse;
import com.enigma.challenge_tokonyadia_api.entity.Store;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StoreService {
    StoreResponse createNew(NewStoreRequest request);
    Store getById(String id);
    StoreResponse getOne(String id);
    Page<StoreResponse> getAll(SearchStoreRequest request);
    StoreResponse update(UpdateStoreRequest request);
    void deleteById(String id);
}
