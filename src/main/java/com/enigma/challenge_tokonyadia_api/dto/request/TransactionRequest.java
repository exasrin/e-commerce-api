package com.enigma.challenge_tokonyadia_api.dto.request;

import com.enigma.challenge_tokonyadia_api.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private String customerId;
    private List<TransactionDetailRequest> transactionDetails;
}
