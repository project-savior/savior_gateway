package com.jerry.savior_gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * @author 22454
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SaviorGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaviorGatewayApplication.class, args);
    }

    @RestController
    public static class Rest{
        @GetMapping("/test")
        public String test(){
            return "test";
        }
    }

}
