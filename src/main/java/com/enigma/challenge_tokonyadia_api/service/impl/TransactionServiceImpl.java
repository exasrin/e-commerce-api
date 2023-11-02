package com.enigma.challenge_tokonyadia_api.service.impl;

import com.enigma.challenge_tokonyadia_api.dto.request.TransactionDetailRequest;
import com.enigma.challenge_tokonyadia_api.dto.request.TransactionRequest;
import com.enigma.challenge_tokonyadia_api.entity.Customer;
import com.enigma.challenge_tokonyadia_api.entity.Product;
import com.enigma.challenge_tokonyadia_api.entity.Transaction;
import com.enigma.challenge_tokonyadia_api.entity.TransactionDetail;
import com.enigma.challenge_tokonyadia_api.repository.TransactionRepository;
import com.enigma.challenge_tokonyadia_api.service.ProductService;
import com.enigma.challenge_tokonyadia_api.service.TransactionDetailService;
import com.enigma.challenge_tokonyadia_api.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ProductService productService;
    private final TransactionDetailService transactionDetailService;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, ProductService productService, TransactionDetailService transactionDetailService) {
        this.transactionRepository = transactionRepository;
        this.productService = productService;
        this.transactionDetailService = transactionDetailService;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Transaction createNewTransaction(TransactionRequest request) {
        Transaction transaction = new Transaction();

        Customer customer = new Customer();
        customer.setId(request.getCustomerId());
        transaction.setCustomer(customer);
        transaction.setTransactionDate(LocalDateTime.now());

        List<TransactionDetail> transactionDetails = new ArrayList<>();
        for (TransactionDetailRequest transactionDetailRequest : request.getTransactionDetails()) {
            TransactionDetail transactionDetail = new TransactionDetail();

            Product product = productService.getById(transactionDetailRequest.getProductId());
            product.setId(transactionDetailRequest.getProductId());
            transactionDetail.setProduct(product);
            transactionDetail.setPrice(product.getPrice());
            transactionDetail.setQuantity(transactionDetailRequest.getQuantity());
            if(transactionDetailRequest.getQuantity() > product.getStock()) {
                throw new RuntimeException();
            }
            product.setStock(product.getStock() - transactionDetailRequest.getQuantity());
            transactionDetails.add(transactionDetail);
        }

        transaction.setTransactionDetails(transactionDetails);

        transactionDetailService.createBulk(transaction.getTransactionDetails());
        transaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.saveAndFlush(transaction);

        for (TransactionDetail transactionDetail : transaction.getTransactionDetails()) {
            transactionDetail.setTransaction(transaction);
        }
        return transaction;
    }
}
