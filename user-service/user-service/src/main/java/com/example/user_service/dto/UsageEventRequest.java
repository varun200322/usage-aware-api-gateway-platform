package com.example.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsageEventRequest {
    @NotBlank
    private String tenantId;

    @NotBlank
    private String path;

    @NotBlank
    private String method;

    @NotNull
    private Integer statusCode;

    @NotNull
    private Instant timestamp;

    @NotNull
    private Long latencyMs;
}
