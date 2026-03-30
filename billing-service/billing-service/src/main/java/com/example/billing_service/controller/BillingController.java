package com.example.billing_service.controller;

import com.example.billing_service.dto.BillingSummaryResponse;
import com.example.billing_service.service.BillingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingController {
    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping("/billing/summary/{tenantId}")
    public BillingSummaryResponse getBillingSummary(@PathVariable String tenantId) {
        return billingService.getBillingSummary(tenantId);
    }
}