package com.library.catalog_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main application class for Catalog Service
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.library.catalog_service", "com.library.common"})
public class CatalogServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }
}





