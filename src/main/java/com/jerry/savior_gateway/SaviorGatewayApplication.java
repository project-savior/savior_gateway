package com.jerry.savior_gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.Collections;

@EnableDiscoveryClient
@SpringBootApplication
public class SaviorGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaviorGatewayApplication.class, args);
    }

}
