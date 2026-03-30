package com.example.gateway_service.resolver;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class TenantKeyResolver implements KeyResolver {
    private static final String DEFAULT_TENANT = "anonymous";

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return exchange.getPrincipal()
                .cast(JwtAuthenticationToken.class)
                .map(token -> token.getToken().getClaimAsString("tenant"))
                .filter(tenant -> tenant != null && !tenant.isBlank())
                .defaultIfEmpty(DEFAULT_TENANT);
    }
}
