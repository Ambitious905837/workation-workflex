package com.workflex.workstation.integration;

import com.workflex.workstation.model.Workation;
import com.workflex.workstation.repository.WorkationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WorkationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WorkationRepository workationRepository;

    @BeforeEach
    public void setup() {
        // Clean the database before each test
        workationRepository.deleteAll();
        
        // Insert test data with enum values that match exactly
        workationRepository.saveAll(Arrays.asList(
            createWorkation("W001", "John Doe", "Germany", "Spain", 
                LocalDate.of(2023, 6, 1), LocalDate.of(2023, 6, 30), 20, Workation.RiskLevel.LOW),
            createWorkation("W002", "Jane Smith", "France", "Italy", 
                LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 15), 10, Workation.RiskLevel.HIGH),
            createWorkation("W003", "Alex Johnson", "Sweden", "Norway", 
                LocalDate.of(2023, 8, 1), LocalDate.of(2023, 8, 20), 15, Workation.RiskLevel.NO)
        ));
    }

    @AfterEach
    public void cleanup() {
        workationRepository.deleteAll();
    }

    @Test
    public void testGetAllWorkations() throws Exception {
        // Fixed URL path - removed the redundant /api prefix since it's already in context-path
        mockMvc.perform(get("/workflex/workation")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].workationId").exists())
                .andExpect(jsonPath("$[0].employee").exists())
                .andExpect(jsonPath("$[0].origin").exists())
                .andExpect(jsonPath("$[0].destination").exists())
                .andExpect(jsonPath("$[0].start").exists())
                .andExpect(jsonPath("$[0].end").exists())
                .andExpect(jsonPath("$[0].workingDays").exists())
                .andExpect(jsonPath("$[0].risk").exists());
    }

    @Test
    public void testGetAllWorkations_SortByEmployee() throws Exception {
        mockMvc.perform(get("/workflex/workation")
                .param("sortBy", "employee")
                .param("sortDirection", "asc")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].employee").value("Alex Johnson"))
                .andExpect(jsonPath("$[1].employee").value("Jane Smith"))
                .andExpect(jsonPath("$[2].employee").value("John Doe"));
    }

    @Test
    public void testGetAllWorkations_SortByOrigin() throws Exception {
        mockMvc.perform(get("/workflex/workation")
                .param("sortBy", "origin")
                .param("sortDirection", "desc")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].origin").value("Sweden"))
                .andExpect(jsonPath("$[1].origin").value("Germany"))
                .andExpect(jsonPath("$[2].origin").value("France"));
    }

    private Workation createWorkation(String id, String employee, String origin, String destination, 
                                     LocalDate start, LocalDate end, Integer workingDays, Workation.RiskLevel risk) {
        Workation workation = new Workation();
        workation.setWorkationId(id);
        workation.setEmployee(employee);
        workation.setOrigin(origin);
        workation.setDestination(destination);
        workation.setStart(start);
        workation.setEnd(end);
        workation.setWorkingDays(workingDays);
        workation.setRisk(risk);
        return workation;
    }
} 