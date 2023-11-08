package com.enigma.challenge_tokonyadia_api.dto.response;

import com.enigma.challenge_tokonyadia_api.entity.TransactionDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private String transactionId;
    private String customerId;
    private LocalDateTime transactionDate;
    private List<TransactionDetail> transactionDetails;
}
