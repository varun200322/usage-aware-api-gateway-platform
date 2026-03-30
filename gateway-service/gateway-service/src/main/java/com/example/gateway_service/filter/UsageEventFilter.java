package com.example.gateway_service.filter;

import com.example.gateway_service.dto.UsageEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class UsageEventFilter implements GlobalFilter, Ordered {
    private final WebClient webClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();

        return chain.filter(exchange)
                .then(exchange.getPrincipal()
                        .cast(JwtAuthenticationToken.class)
                        .map(jwt -> {
                            String tenant = jwt.getToken().getClaimAsString("tenant");
                            return (tenant != null && !tenant.isBlank()) ? tenant : "anonymous";
                        })
                        .defaultIfEmpty("anonymous")
                        .flatMap(tenantId -> {
                            UsageEventDTO usageEvent = buildUsageEvent(exchange, startTime, tenantId);
                            return sendUsageEvent(usageEvent);
                        }));
    }

    private UsageEventDTO buildUsageEvent(ServerWebExchange exchange, long startTime, String tenantId) {
        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod() != null
                ? exchange.getRequest().getMethod().name()
                : "UNKNOWN";
        int statusCode = exchange.getResponse().getStatusCode() != null
                ? exchange.getResponse().getStatusCode().value()
                : 500;
        long latencyMs = System.currentTimeMillis() - startTime;

        return UsageEventDTO.builder()
                .tenantId(tenantId)
                .path(path)
                .method(method)
                .statusCode(statusCode)
                .timestamp(Instant.now())
                .latencyMs(latencyMs)
                .build();
    }

    private Mono<Void> sendUsageEvent(UsageEventDTO usageEvent) {
        return webClient.post()
                .uri("http://user-service:8082/internal/usage")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(usageEvent)
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorResume(error -> Mono.empty());
    }

    @Override
    public int getOrder() {
        return -1;
    }
}