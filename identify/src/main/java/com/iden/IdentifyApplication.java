package com.iden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.common.*",
        "com.iden.*"
})
@EnableFeignClients
public class IdentifyApplication {
    public static void main(String[] args) {
        SpringApplication.run(IdentifyApplication.class, args);
    }
}