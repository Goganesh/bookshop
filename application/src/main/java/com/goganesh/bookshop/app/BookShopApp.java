package com.goganesh.bookshop.app;

import com.goganesh.bookshop.model.configuration.ModelConfiguration;
import com.goganesh.bookshop.webapi.client.WebApiClientConfiguration;
import com.goganesh.bookshop.webui.WebUiConfiguration;
import com.goganesh.migration.configuration.MigrationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ModelConfiguration.class, WebApiClientConfiguration.class,
        MigrationConfiguration.class, WebUiConfiguration.class})
public class BookShopApp {
    public static void main(String[] args) {
        SpringApplication.run(BookShopApp.class);
    }

}
