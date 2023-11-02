package com.enigma.challenge_tokonyadia_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchProductRequest {
    private Integer page;
    private Integer size;
    private String direction;
    private String sortBy;
    private String name;
    private Long minPrice;
    private Long maxPrice;
}
