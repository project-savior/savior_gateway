package com.jerry.savior_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 22454
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class SaviorGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaviorGatewayApplication.class, args);
    }

}
