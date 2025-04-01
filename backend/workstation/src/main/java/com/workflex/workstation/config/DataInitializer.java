package com.workflex.workstation.config;

import com.workflex.workstation.service.CsvImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class DataInitializer {
    
    private final CsvImportService csvImportService;
    
    @Bean
    @Order(2) // Make sure this runs after Hibernate has set up the schema
    public CommandLineRunner initDatabase() {
        return args -> {
            log.info("Initializing database with CSV data...");
            csvImportService.importWorkations();
        };
    }
} 