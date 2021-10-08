package com.jerry.savior_gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerry.savior_common.util.ObjectMapperHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 22454
 */
@Configuration
public class ObjectMapperHelperConfig {
    private final ObjectMapper objectMapper;

    public ObjectMapperHelperConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    @ConditionalOnMissingBean
    public ObjectMapperHelper objectMapperHelper() {
        return new ObjectMapperHelper(objectMapper);
    }
}
