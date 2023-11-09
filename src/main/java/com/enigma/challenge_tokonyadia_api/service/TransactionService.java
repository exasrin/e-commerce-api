package com.enigma.challenge_tokonyadia_api.service;

import com.enigma.challenge_tokonyadia_api.dto.request.SearchTransactionRequest;
import com.enigma.challenge_tokonyadia_api.dto.request.TransactionRequest;
import com.enigma.challenge_tokonyadia_api.dto.response.TransactionResponse;
import com.enigma.challenge_tokonyadia_api.entity.Transaction;
import com.enigma.challenge_tokonyadia_api.entity.TransactionDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {
    TransactionResponse createNewTransaction(TransactionRequest request);
    Page<TransactionResponse> getAll(SearchTransactionRequest request);
    TransactionResponse getById(String id);
}
