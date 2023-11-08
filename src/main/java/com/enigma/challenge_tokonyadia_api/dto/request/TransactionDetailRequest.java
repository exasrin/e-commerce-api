package com.enigma.challenge_tokonyadia_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetailRequest {
    @NotBlank(message = "product id os required")
    private String productId;
    @NotBlank(message = "quantity is required")
    @Min(value = 1, message = "quantity must be greater than 0")
    @Max(value = 100, message = "quantity must be less than 100")
    private Integer quantity;
}
