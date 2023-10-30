package com.enigma.challenge_tokonyadia_api.service;

import com.enigma.challenge_tokonyadia_api.entity.Transaction;
import com.enigma.challenge_tokonyadia_api.entity.TransactionDetail;

import java.util.List;

public interface TransactionDetailService {
    List<TransactionDetail> createBulk(List<TransactionDetail> transactionDetails);
}
