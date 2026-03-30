package com.example.billing_service.service;

import com.example.billing_service.dto.BillingSummaryResponse;
import com.example.billing_service.dto.UsageEventResponse;
import com.example.billing_service.model.PricingPlan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class BillingService {
    private final WebClient webClient;
    private final PricingPlanService pricingPlanService;

    public BillingService(WebClient webClient, PricingPlanService pricingPlanService) {
        this.webClient = webClient;
        this.pricingPlanService = pricingPlanService;
    }

    public BillingSummaryResponse getBillingSummary(String tenantId) {
        List<UsageEventResponse> usageEvents = fetchUsageEventsByTenant(tenantId);

        long totalRequests = usageEvents.size();

        long successfulRequests = usageEvents.stream()
                .filter(this::isSuccessful)
                .count();

        long failedRequests = totalRequests - successfulRequests;

        PricingPlan plan = pricingPlanService.getPlanForTenant(tenantId);
        long freeRequestLimit = pricingPlanService.getFreeRequestLimit(plan);
        double costPerExtraRequest = pricingPlanService.getCostPerExtraSuccessfulRequest(plan);

        long billableRequests = Math.max(0, successfulRequests - freeRequestLimit);
        double totalCost = billableRequests * costPerExtraRequest;

        return BillingSummaryResponse.builder()
                .tenantId(tenantId)
                .plan(plan.name())
                .totalRequests(totalRequests)
                .successfulRequests(successfulRequests)
                .failedRequests(failedRequests)
                .freeRequestLimit(freeRequestLimit)
                .billableRequests(billableRequests)
                .totalCost(totalCost)
                .build();
    }

    private List<UsageEventResponse> fetchUsageEventsByTenant(String tenantId) {
        List<UsageEventResponse> response = webClient.get()
                .uri("http://user-service:8082/usage/tenant/{tenantId}", tenantId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UsageEventResponse>>() {})
                .block();

        return response != null ? response : List.of();
    }

    private boolean isSuccessful(UsageEventResponse event) {
        return event.getStatusCode() >= 200 && event.getStatusCode() < 300;
    }
}