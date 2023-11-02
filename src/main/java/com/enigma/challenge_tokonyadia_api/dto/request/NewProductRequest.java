package com.enigma.challenge_tokonyadia_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewProductRequest {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "description is required")
    private String description;

    @NotNull(message = "price is required")
    @Min(value = 0, message = "price must be greater than or equal 0")
    private Long price;

    @NotBlank(message = "stock is required")
    @Min(value = 0, message = "stock must be greater than 0")
    private Integer stock;
}
