package com.goganeah.bookshop.app;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class BookShopApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BookShopApp.class);
    }

    @Override
    public void run(String... args) {
    }
}
