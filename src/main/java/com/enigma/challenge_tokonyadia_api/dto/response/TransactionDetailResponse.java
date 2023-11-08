package com.enigma.challenge_tokonyadia_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetailResponse {
    private String transactionDetailId;
    private String transactionId;
    private String productId;
    private Long price;
    private Integer quantity;
}
