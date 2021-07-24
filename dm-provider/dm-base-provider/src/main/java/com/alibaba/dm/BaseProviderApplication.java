package com.alibaba.dm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BaseProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseProviderApplication.class,args);
    }

}
