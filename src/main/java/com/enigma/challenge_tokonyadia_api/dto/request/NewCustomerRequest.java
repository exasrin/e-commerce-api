package com.enigma.challenge_tokonyadia_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCustomerRequest {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "address is required")
    private String address;

    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "phone number is required")
    @Size(min = 11, max = 15, message = "invalid phone number")
    private String phoneNumber;
}
