package com.example.billing_service.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingSummaryResponse {
    private String tenantId;
    private String plan;
    private long totalRequests;
    private long successfulRequests;
    private long failedRequests;
    private long freeRequestLimit;
    private long billableRequests;
    private double totalCost;
}