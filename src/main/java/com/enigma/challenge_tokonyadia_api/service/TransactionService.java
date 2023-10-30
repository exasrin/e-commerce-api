package com.enigma.challenge_tokonyadia_api.service;

import com.enigma.challenge_tokonyadia_api.dto.request.TransactionRequest;
import com.enigma.challenge_tokonyadia_api.entity.Transaction;
import com.enigma.challenge_tokonyadia_api.entity.TransactionDetail;

import java.util.List;

public interface TransactionService {
    Transaction createNewTransaction(TransactionRequest request);
}
