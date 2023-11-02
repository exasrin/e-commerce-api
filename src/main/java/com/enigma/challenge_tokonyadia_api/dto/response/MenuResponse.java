package com.enigma.challenge_tokonyadia_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuResponse {
    private String menuId;
    private String name;
    private Long price;
}
