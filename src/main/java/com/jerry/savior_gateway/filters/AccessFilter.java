package com.jerry.savior_gateway.filters;

import com.jerry.savior_common.constants.StandardResponse;
import com.jerry.savior_common.defaultImpl.DefaultTokenExtractor;
import com.jerry.savior_common.error.BusinessException;
import com.jerry.savior_common.interfaces.IResponseEnum;
import com.jerry.savior_common.response.CommonResponse;
import com.jerry.savior_common.util.ObjectMapperHelper;
import com.jerry.savior_gateway.config.UnCheckedApiConfig;
import com.jerry.savior_gateway.error.EnumGatewayException;
import com.jerry.savior_gateway.openApi.SecurityOpenApi;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * @author 22454
 */
@Slf4j
@Getter
@Component
public class AccessFilter implements GlobalFilter {
    public static final String INTERNAL_ACCESS_HEADER_NAME = "internal-access";
    public static final String[] INTERNAL_ACCESS_HEADER_VALUE = new String[]{"true"};
    private final SecurityOpenApi securityOpenApi;
    private final ObjectMapperHelper objectMapperHelper;
    private final UnCheckedApiConfig unCheckedApiConfig;

    public AccessFilter(SecurityOpenApi securityOpenApi,
                        ObjectMapperHelper objectMapperHelper,
                        UnCheckedApiConfig unCheckedApiConfig) {
        this.securityOpenApi = securityOpenApi;
        this.objectMapperHelper = objectMapperHelper;
        this.unCheckedApiConfig = unCheckedApiConfig;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        try {

            URI uri = request.getURI();
            String path = uri.getPath();
            InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
            log.info("{} ==> {}", remoteAddress, path);

            // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????
            request = exchange.getRequest()
                    .mutate()
                    .header(INTERNAL_ACCESS_HEADER_NAME, INTERNAL_ACCESS_HEADER_VALUE)
                    .build();
            Mono<Void> ok = chain.filter(
                    exchange.mutate()
                            .request(
                                    request.mutate()
                                            .build()
                            )
                            .build()
            );
            // ????????????????????????????????????
            if (unCheckedApiConfig.isUnchecked(path)) {
                log.info("{} ????????????????????????????????????", path);
                return ok;
            }
            // ?????? token
            HttpHeaders headers = request.getHeaders();
            String token = headers.getFirst(DefaultTokenExtractor.HEADER_ATTRIBUTE_NAME);
            // ??????token??????????????????????????????
            if (StringUtils.isBlank(token)) {
                return getVoidMono(response, EnumGatewayException.UNAUTHORIZED);
            }
            // ??????
            CommonResponse<Void> authentication = securityOpenApi.authentication(token);
            // ???????????????????????????????????????
            if (!authentication.isSuccess()) {
                return getVoidMono(response, authentication);
            }

            // ?????????????????????
            return ok;
        } catch (BusinessException ex) {
            return getVoidMono(response, StandardResponse.ERROR);
        } catch (Exception e) {
            log.error("???????????????", e);
            return getVoidMono(response, StandardResponse.ERROR);
        }
    }

    private Mono<Void> getVoidMono(ServerHttpResponse response, IResponseEnum responseEnum) {
        Integer code = responseEnum.getCode();
        String message = responseEnum.getMessage();
        CommonResponse<Void> commonResponse = CommonResponse
                .build(code, null, message);
        return getVoidMono(response, commonResponse);
    }

    private Mono<Void> getVoidMono(ServerHttpResponse response, CommonResponse<?> commonResponse) {
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        DataBuffer dataBuffer = response
                .bufferFactory()
                .wrap(objectMapperHelper
                        .toJson(commonResponse)
                        .getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Flux.just(dataBuffer));
    }
}
