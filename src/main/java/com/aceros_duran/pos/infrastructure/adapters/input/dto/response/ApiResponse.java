package com.aceros_duran.pos.infrastructure.adapters.input.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ApiResponse<T> {
    private Map<String, T> response;
}
