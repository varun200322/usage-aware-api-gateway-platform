package com.example.user_service.mapper;

import com.example.user_service.dto.UsageEventRequest;
import com.example.user_service.model.UsageEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UsageEventMapper {
    public UsageEvent toModel(UsageEventRequest request) {
        return UsageEvent.builder()
                .id(UUID.randomUUID())
                .tenantId(request.getTenantId())
                .path(request.getPath())
                .method(request.getMethod())
                .statusCode(request.getStatusCode())
                .timestamp(request.getTimestamp())
                .latencyMs(request.getLatencyMs())
                .build();
    }
}