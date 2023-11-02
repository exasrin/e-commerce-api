package com.enigma.challenge_tokonyadia_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private String customerId;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
}
