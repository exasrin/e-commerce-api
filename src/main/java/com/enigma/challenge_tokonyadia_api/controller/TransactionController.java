package com.enigma.challenge_tokonyadia_api.controller;

import com.enigma.challenge_tokonyadia_api.dto.request.TransactionRequest;
import com.enigma.challenge_tokonyadia_api.entity.Transaction;
import com.enigma.challenge_tokonyadia_api.service.TransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @PostMapping("/api/transactions")
    public Transaction createNewTransaction(@RequestBody TransactionRequest request) {
        return transactionService.createNewTransaction(request);
    }

}
