package com.example.gateway_service.config;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class FallBackController {
    @GetMapping("/fallback/orders")
    public String ordersFallback() {
        return "Orders service is currently unavailable. Please try again later.";
    }
}
