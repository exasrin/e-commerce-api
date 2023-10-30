package com.enigma.challenge_tokonyadia_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailRequest {
    private String productId;
    private Integer quantity;
    private Long price;
}
