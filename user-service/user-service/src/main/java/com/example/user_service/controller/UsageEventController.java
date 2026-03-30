package com.example.user_service.controller;

import com.example.user_service.dto.UsageEventRequest;
import com.example.user_service.model.UsageEvent;
import com.example.user_service.service.UsageEventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsageEventController {
    private final UsageEventService usageEventService;

    public UsageEventController(UsageEventService usageEventService) {
        this.usageEventService = usageEventService;
    }

    @PostMapping("/internal/usage")
    @ResponseStatus(HttpStatus.CREATED)
    public UsageEvent createUsageEvent(@Valid @RequestBody UsageEventRequest request) {
        return usageEventService.createUsageEvent(request);
    }

    @GetMapping("/usage")
    public List<UsageEvent> getAllUsageEvents() {
        return usageEventService.getAllUsageEvents();
    }

    @GetMapping("/usage/tenant/{tenantId}")
    public List<UsageEvent> getUsageEventsByTenantId(@PathVariable String tenantId) {
        return usageEventService.getUsageEventsByTenantId(tenantId);
    }
}
