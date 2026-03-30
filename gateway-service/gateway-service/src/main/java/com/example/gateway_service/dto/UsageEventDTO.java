package com.example.gateway_service.dto;

import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsageEventDTO {

    private String tenantId;
    private String path;
    private String method;
    private int statusCode;
    private Instant timestamp;
    private long latencyMs;
}
