package com.enigma.challenge_tokonyadia_api.controller;

import com.enigma.challenge_tokonyadia_api.dto.request.TransactionRequest;
import com.enigma.challenge_tokonyadia_api.dto.response.CommonResponse;
import com.enigma.challenge_tokonyadia_api.dto.response.TransactionResponse;
import com.enigma.challenge_tokonyadia_api.entity.Transaction;
import com.enigma.challenge_tokonyadia_api.service.TransactionDetailService;
import com.enigma.challenge_tokonyadia_api.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionDetailService transactionDetailService;

    @PostMapping
    public ResponseEntity<?> createNewTransaction(@RequestBody TransactionRequest request) {
        TransactionResponse transactionResponse = transactionService.createNewTransaction(request);
        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .message("successfully created new transaction")
                .statusCode(HttpStatus.CREATED.value())
                .data(transactionResponse)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

}
