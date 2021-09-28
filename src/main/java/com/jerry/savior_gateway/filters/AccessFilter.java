package com.jerry.savior_gateway.filters;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;

/**
 * @author 22454
 */
@Slf4j
@Getter
@Component
public class AccessFilter implements GlobalFilter {
    private final String GATEWAY_ACCESS_HEADER_NAME = "gateway-access";
    private final String[] GATEWAY_ACCESS_HEADER_VALUE = new String[]{"true"};

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI uri = exchange.getRequest().getURI();
        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
        log.info("{} ==> {}", remoteAddress, uri);
        //TODO 检测 token

        // 加上网关访问参数，与后端服务配合，达到阻止客户端可以直接访问后端服务的效果
        ServerHttpRequest request = exchange.getRequest()
                .mutate()
                .header(GATEWAY_ACCESS_HEADER_NAME, GATEWAY_ACCESS_HEADER_VALUE)
                .build();
        return chain.filter(
                exchange.mutate()
                        .request(
                                request.mutate()
                                        .build()
                        )
                        .build()
        );
    }
}
