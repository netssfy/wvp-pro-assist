package com.sescity.vbase.assist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VbaseAssistApplication {

    public static void main(String[] args) {
        SpringApplication.run(VbaseAssistApplication.class, args);
    }

}
