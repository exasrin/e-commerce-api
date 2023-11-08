package com.enigma.challenge_tokonyadia_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private String productId;
    private String name;
    private String description;
    private Long price;
    private Integer stock;
    private FileResponse image;
}
