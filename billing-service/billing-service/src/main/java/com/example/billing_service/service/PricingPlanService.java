package com.example.billing_service.service;

import com.example.billing_service.model.PricingPlan;
import org.springframework.stereotype.Service;

@Service
public class PricingPlanService {
    public PricingPlan getPlanForTenant(String tenantId) {
        if (tenantId == null || tenantId.isBlank()) {
            return PricingPlan.FREE;
        }
        return switch (tenantId.toLowerCase()) {
            case "pro" -> PricingPlan.PRO;
            case "enterprise" -> PricingPlan.ENTERPRISE;
            default -> PricingPlan.FREE;
        };
    }

    public long getFreeRequestLimit(PricingPlan plan) {
        return switch (plan) {
            case FREE -> 100;
            case PRO -> 1000;
            case ENTERPRISE -> 10000;
        };
    }

    public double getCostPerExtraSuccessfulRequest(PricingPlan plan) {
        return switch (plan) {
            case FREE -> 0.02;
            case PRO -> 0.01;
            case ENTERPRISE -> 0.005;
        };
    }
}