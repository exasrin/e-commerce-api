package com.enigma.challenge_tokonyadia_api.service.impl;

import com.enigma.challenge_tokonyadia_api.dto.request.NewStoreRequest;
import com.enigma.challenge_tokonyadia_api.dto.request.SearchStoreRequest;
import com.enigma.challenge_tokonyadia_api.dto.request.UpdateStoreRequest;
import com.enigma.challenge_tokonyadia_api.dto.response.StoreResponse;
import com.enigma.challenge_tokonyadia_api.entity.Customer;
import com.enigma.challenge_tokonyadia_api.entity.Store;
import com.enigma.challenge_tokonyadia_api.repository.StoreRepository;
import com.enigma.challenge_tokonyadia_api.service.StoreService;
import com.enigma.challenge_tokonyadia_api.util.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final ValidationUtil validationUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreResponse createNew(NewStoreRequest request) {
        try {
            validationUtil.validate(request);
            Store store = Store.builder()
                    .name(request.getName())
                    .address(request.getAddress())
                    .siupNumber(request.getSiupNumber())
                    .phoneNumber(request.getPhoneNumber())
                    .build();
            storeRepository.saveAndFlush(store);
            return mapToResponse(store);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "siup number or phone number already exist");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Store getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    @Transactional(readOnly = true)
    public StoreResponse getOne(String id) {
        validationUtil.validate(id);
        Store store = findByIdOrThrowNotFound(id);
        return mapToResponse(store);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StoreResponse> getAll(SearchStoreRequest request) {
        for (Field declaredField : Store.class.getDeclaredFields()) {
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

        Page<Store> stores = storeRepository.findAll(pageable);
        return stores.map(this::mapToResponse);
    }

    @Override
    public StoreResponse update(UpdateStoreRequest request) {
        try {
            Store currentStore = findByIdOrThrowNotFound(request.getId());
            currentStore.setId(request.getId());
            currentStore.setName(request.getName());
            currentStore.setSiupNumber(request.getSiupNumber());
            currentStore.setAddress(request.getAddress());
            currentStore.setPhoneNumber(request.getPhoneNumber());
            storeRepository.saveAndFlush(currentStore);
            return mapToResponse(currentStore);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "siup number or phone number already exist");
        }
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

    private StoreResponse mapToResponse(Store store) {
        return StoreResponse.builder()
                .storeId(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .phoneNumber(store.getPhoneNumber())
                .siupNumber(store.getSiupNumber())
                .build();
    }
}
