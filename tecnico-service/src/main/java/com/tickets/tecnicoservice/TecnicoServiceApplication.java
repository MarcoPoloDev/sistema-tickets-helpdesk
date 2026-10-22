package com.tickets.tecnicoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TecnicoServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TecnicoServiceApplication.class, args);
    }
}
