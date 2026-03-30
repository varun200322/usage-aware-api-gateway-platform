package com.example.orders.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
public class OrderController {
    @GetMapping("/api/orders/{id}")
    public Map<String, Object> getOrderById(@PathVariable Long id) {
        return Map.of(
                "orderId", id,
                "status", "CREATED",
                "service", "orders-service"
        );
    }
}