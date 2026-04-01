package com.example.gateway_service.config;

import com.example.gateway_service.resolver.TenantKeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class RouteConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
                                           TenantKeyResolver tenantKeyResolver,
                                           RedisRateLimiter redisRateLimiter) {
        return builder.routes()
                .route("orders-service", r -> r
                        .path("/api/orders/**")
                        .filters(f -> f
                                .requestRateLimiter(config -> config
                                        .setKeyResolver(tenantKeyResolver)
                                        .setRateLimiter(redisRateLimiter)
                                )
                                .retry(retryConfig->retryConfig
                                        .setRetries(2)
                                        .setStatuses(HttpStatus.BAD_GATEWAY,
                                                HttpStatus.SERVICE_UNAVAILABLE,
                                                HttpStatus.GATEWAY_TIMEOUT))
                                .circuitBreaker(cb -> cb
                                        .setName("ordersCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/orders")
                                )
                        )
                        .uri("http://orders-service:8081") //routing target
                )
                .route("transactions-service", r -> r
                .path("/api/transactions/**")
                .uri("http://billing-service:8083"))
                .build();
    }
}