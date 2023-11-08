package com.enigma.challenge_tokonyadia_api.service.impl;

import com.enigma.challenge_tokonyadia_api.dto.response.TransactionDetailResponse;
import com.enigma.challenge_tokonyadia_api.dto.response.TransactionResponse;
import com.enigma.challenge_tokonyadia_api.entity.TransactionDetail;
import com.enigma.challenge_tokonyadia_api.repository.TransactionDetailRepository;
import com.enigma.challenge_tokonyadia_api.service.TransactionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionDetailServiceImpl implements TransactionDetailService {
    private final TransactionDetailRepository transactionDetailRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<TransactionDetail> createBulk(List<TransactionDetail> transactionDetails) {
        return transactionDetailRepository.saveAllAndFlush(transactionDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionDetailResponse getById(String id) {
        TransactionDetail transactionDetail = transactionDetailRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "transaction cetail not found"));
        return TransactionDetailResponse.builder()
                .transactionDetailId(transactionDetail.getId())
                .transactionId(transactionDetail.getTransaction().getId())
                .productId(transactionDetail.getProduct().getId())
                .price(transactionDetail.getPrice())
                .quantity(transactionDetail.getQuantity())
                .build();
    }
}
