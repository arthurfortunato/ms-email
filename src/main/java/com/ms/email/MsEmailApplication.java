package com.ms.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ms.email.*")
public class MsEmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsEmailApplication.class, args);
    }

}
