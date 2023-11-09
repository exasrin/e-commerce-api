package com.enigma.challenge_tokonyadia_api.controller;

import com.enigma.challenge_tokonyadia_api.dto.request.SearchTransactionRequest;
import com.enigma.challenge_tokonyadia_api.dto.request.TransactionRequest;
import com.enigma.challenge_tokonyadia_api.dto.response.CommonResponse;
import com.enigma.challenge_tokonyadia_api.dto.response.PagingResponse;
import com.enigma.challenge_tokonyadia_api.dto.response.TransactionDetailResponse;
import com.enigma.challenge_tokonyadia_api.dto.response.TransactionResponse;
import com.enigma.challenge_tokonyadia_api.entity.Transaction;
import com.enigma.challenge_tokonyadia_api.service.TransactionDetailService;
import com.enigma.challenge_tokonyadia_api.service.TransactionService;
import com.enigma.challenge_tokonyadia_api.util.PagingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<?> getAllTransaction(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        page = PagingUtil.validatePage(page);
        size = PagingUtil.validateSize(size);

        SearchTransactionRequest request = SearchTransactionRequest.builder()
                .page(page)
                .size(size)
                .build();
        Page<TransactionResponse> transactionResponses = transactionService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .count(transactionResponses.getTotalElements())
                .totalPages(transactionResponses.getTotalPages())
                .build();

        CommonResponse<List<TransactionResponse>> response = CommonResponse.<List<TransactionResponse>>builder()
                .message("successfully get all transaction")
                .statusCode(HttpStatus.OK.value())
                .data(transactionResponses.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable String id) {
        TransactionResponse transactionResponse = transactionService.getById(id);

        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .message("successfully get transaction by id")
                .statusCode(HttpStatus.OK.value())
                .data(transactionResponse)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getDetailById(@PathVariable String id) {
        TransactionDetailResponse transactionDetailResponse = transactionDetailService.getById(id);
        CommonResponse<TransactionDetailResponse> response = CommonResponse.<TransactionDetailResponse>builder()
                .message("successfully get detail transaction by id")
                .statusCode(HttpStatus.OK.value())
                .data(transactionDetailResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


}
