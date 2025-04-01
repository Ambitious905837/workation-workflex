package com.workflex.workstation.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.workflex.workstation.model.Workation;
import com.workflex.workstation.repository.WorkationRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CsvImportService {

    private final WorkationRepository workationRepository;
    private final ResourceLoader resourceLoader;

    @Value("${workation.csv.path}")
    private String csvPath;

    private static final DateTimeFormatter CSV_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Use ApplicationReadyEvent instead of PostConstruct to ensure DB is fully initialized
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void importWorkations() {
        try {
            if (workationRepository.count() > 0) {
                log.info("Workations already imported. Skipping import.");
                return;
            }
            
            Resource resource = resourceLoader.getResource(csvPath);
            if (!resource.exists()) {
                log.error("CSV file not found at path: {}", csvPath);
                return;
            }
            
            log.info("Starting CSV import from: {}", csvPath);
            List<Workation> workations = parseWorkationsFromCsv(resource);
            workationRepository.saveAll(workations);
            log.info("Successfully imported {} workations from CSV", workations.size());
        } catch (Exception e) {
            log.error("Failed to import workations from CSV: {}", e.getMessage(), e);
        }
    }

    private List<Workation> parseWorkationsFromCsv(Resource resource) throws IOException, CsvValidationException {
        List<Workation> workations = new ArrayList<>();
        
        try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            // Skip header
            String[] header = reader.readNext();
            log.info("CSV headers: {}", String.join(", ", header));
            
            String[] line;
            while ((line = reader.readNext()) != null) {
                try {
                    Workation workation = new Workation();
                    workation.setWorkationId(line[0]);
                    workation.setEmployee(line[1]);
                    workation.setOrigin(line[2]);
                    workation.setDestination(line[3]);
                    workation.setStart(LocalDate.parse(line[4], CSV_DATE_FORMATTER));
                    workation.setEnd(LocalDate.parse(line[5], CSV_DATE_FORMATTER));
                    workation.setWorkingDays(Integer.parseInt(line[6]));
                    workation.setRisk(Workation.RiskLevel.valueOf(line[7]));
                    
                    workations.add(workation);
                    log.debug("Parsed workation: {}", workation);
                } catch (Exception e) {
                    log.error("Error parsing CSV line: {}", String.join(", ", line), e);
                }
            }
        }
        
        return workations;
    }
} 