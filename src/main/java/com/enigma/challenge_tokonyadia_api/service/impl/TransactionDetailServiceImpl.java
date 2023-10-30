package com.enigma.challenge_tokonyadia_api.service.impl;

import com.enigma.challenge_tokonyadia_api.entity.TransactionDetail;
import com.enigma.challenge_tokonyadia_api.repository.TransactionDetailRepository;
import com.enigma.challenge_tokonyadia_api.service.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionDetailServiceImpl implements TransactionDetailService {
    private final TransactionDetailRepository transactionDetailRepository;
    @Autowired
    public TransactionDetailServiceImpl(TransactionDetailRepository transactionDetailRepository) {
        this.transactionDetailRepository = transactionDetailRepository;
    }

    @Override
    public List<TransactionDetail> createBulk(List<TransactionDetail> transactionDetails) {
        return transactionDetailRepository.saveAllAndFlush(transactionDetails);
    }
}
