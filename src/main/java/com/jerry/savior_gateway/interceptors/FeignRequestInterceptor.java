package com.jerry.savior_gateway.interceptors;

import com.jerry.savior_gateway.filters.AccessFilter;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;


/**
 * @author 22454
 */
@Slf4j
@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.info("request header set internal access attributes");
        // 设置请求头
        requestTemplate.header(AccessFilter.INTERNAL_ACCESS_HEADER_NAME,
                AccessFilter.INTERNAL_ACCESS_HEADER_VALUE);
    }
}
