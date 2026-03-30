package com.example.user_service.repository;

import com.example.user_service.model.UsageEvent;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class UsageEventRepository {
    private final List<UsageEvent> usageEvents = new CopyOnWriteArrayList<>();

    public UsageEvent save(UsageEvent usageEvent) {
        usageEvents.add(usageEvent);
        return usageEvent;
    }

    public List<UsageEvent> findAll() {
        return new ArrayList<>(usageEvents);
    }

    public List<UsageEvent> findByTenantId(String tenantId) {
        return usageEvents.stream()
                .filter(event -> event.getTenantId().equalsIgnoreCase(tenantId))
                .toList();
    }
}