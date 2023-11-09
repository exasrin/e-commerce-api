package com.enigma.challenge_tokonyadia_api.service.impl;

import com.enigma.challenge_tokonyadia_api.dto.request.SearchTransactionRequest;
import com.enigma.challenge_tokonyadia_api.dto.request.TransactionDetailRequest;
import com.enigma.challenge_tokonyadia_api.dto.request.TransactionRequest;
import com.enigma.challenge_tokonyadia_api.dto.response.TransactionDetailResponse;
import com.enigma.challenge_tokonyadia_api.dto.response.TransactionResponse;
import com.enigma.challenge_tokonyadia_api.entity.Customer;
import com.enigma.challenge_tokonyadia_api.entity.Product;
import com.enigma.challenge_tokonyadia_api.entity.Transaction;
import com.enigma.challenge_tokonyadia_api.entity.TransactionDetail;
import com.enigma.challenge_tokonyadia_api.repository.TransactionRepository;
import com.enigma.challenge_tokonyadia_api.service.CustomerService;
import com.enigma.challenge_tokonyadia_api.service.ProductService;
import com.enigma.challenge_tokonyadia_api.service.TransactionDetailService;
import com.enigma.challenge_tokonyadia_api.service.TransactionService;
import com.enigma.challenge_tokonyadia_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ProductService productService;
    private final TransactionDetailService transactionDetailService;
    private final ValidationUtil validationUtil;
    private final CustomerService customerService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse createNewTransaction(TransactionRequest request) {
        validationUtil.validate(request);
        Customer customer = customerService.getById(request.getCustomerId());
        Transaction transaction = Transaction.builder()
                .customer(customer)
                .transactionDate(LocalDateTime.now())
                .build();
        List<TransactionDetail> transactionDetails = new ArrayList<>();
        for (TransactionDetailRequest transactionDetailRequest : request.getTransactionDetails()) {
            Product product = productService.getById(transactionDetailRequest.getProductId());
            TransactionDetail transactionDetail = TransactionDetail.builder()
                    .transaction(transaction)
                    .product(product)
                    .quantity(transactionDetailRequest.getQuantity())
                    .price(product.getPrice())
                    .build();
            transactionDetails.add(transactionDetail);
        }
        transactionDetailService.createBulk(transactionDetails);
        transaction.setTransactionDetails(transactionDetails);

        return mapToTransactionResponse(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponse> getAll(SearchTransactionRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        Page<Transaction> transactions = transactionRepository.findAll(pageable);
        return transactions.map(this::mapToTransactionResponse);
    }

    @Override
    public TransactionResponse getById(String id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "transaction not foun"));
        return mapToTransactionResponse(transaction);
    }

    private TransactionResponse mapToTransactionResponse(Transaction transaction) {
        List<TransactionDetailResponse> transactionDetailResponses = transaction.getTransactionDetails().stream().map(transactionDetail -> {
            return TransactionDetailResponse.builder()
                    .transactionDetailId(transactionDetail.getId())
                    .transactionId(transactionDetail.getTransaction().getId())
                    .productId(transactionDetail.getProduct().getId())
                    .price(transactionDetail.getProduct().getPrice())
                    .quantity(transactionDetail.getQuantity())
                    .build();
        }).collect(Collectors.toList());

        return TransactionResponse.builder()
                .transactionId(transaction.getId())
                .customerId(transaction.getCustomer().getId())
                .transactionDetails(transactionDetailResponses)
                .transactionDate(transaction.getTransactionDate())
                .build();
    }
}
