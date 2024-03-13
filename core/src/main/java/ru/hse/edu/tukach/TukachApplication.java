package ru.hse.edu.tukach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class TukachApplication {

    public static void main(String[] args) {
        SpringApplication.run(TukachApplication.class, args);
    }
}