package com.maorzehavi.locationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LocationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocationServerApplication.class, args);
    }

}
