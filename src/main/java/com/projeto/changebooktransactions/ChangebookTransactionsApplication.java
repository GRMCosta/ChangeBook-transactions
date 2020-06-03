package com.projeto.changebooktransactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ChangebookTransactionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChangebookTransactionsApplication.class, args);
    }

}
