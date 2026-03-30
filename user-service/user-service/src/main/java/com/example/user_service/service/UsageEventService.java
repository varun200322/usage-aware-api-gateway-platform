package com.example.user_service.service;

import com.example.user_service.dto.UsageEventRequest;
import com.example.user_service.model.UsageEvent;
import com.example.user_service.repository.UsageEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsageEventService {
    private final UsageEventRepository usageEventRepository;

    public UsageEventService(UsageEventRepository usageEventRepository) {
        this.usageEventRepository = usageEventRepository;
    }

    public UsageEvent createUsageEvent(UsageEventRequest request) {
        UsageEvent usageEvent = UsageEvent.builder()
                .id(UUID.randomUUID())
                .tenantId(request.getTenantId())
                .path(request.getPath())
                .method(request.getMethod())
                .statusCode(request.getStatusCode())
                .timestamp(request.getTimestamp())
                .latencyMs(request.getLatencyMs())
                .build();

        return usageEventRepository.save(usageEvent);
    }

    public List<UsageEvent> getAllUsageEvents() {
        return usageEventRepository.findAll();
    }

    public List<UsageEvent> getUsageEventsByTenantId(String tenantId) {
        return usageEventRepository.findByTenantId(tenantId);
    }
}