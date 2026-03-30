package com.example.billing_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsageEventResponse {
    private UUID id;
    private String tenantId;
    private String path;
    private String method;
    private int statusCode;
    private Instant timestamp;
    private long latencyMs;
}