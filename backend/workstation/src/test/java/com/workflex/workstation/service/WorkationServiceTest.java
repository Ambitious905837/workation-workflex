package com.workflex.workstation.service;

import com.workflex.workstation.dto.WorkationDTO;
import com.workflex.workstation.model.Workation;
import com.workflex.workstation.repository.WorkationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WorkationServiceTest {

    @Mock
    private WorkationRepository workationRepository;

    @InjectMocks
    private WorkationService workationService;

    private Workation workation1;
    private Workation workation2;

    @BeforeEach
    public void setup() {
        // Create test data
        workation1 = new Workation();
        workation1.setWorkationId("W001");
        workation1.setEmployee("John Doe");
        workation1.setOrigin("Germany");
        workation1.setDestination("Spain");
        workation1.setStart(LocalDate.of(2023, 6, 1));
        workation1.setEnd(LocalDate.of(2023, 6, 30));
        workation1.setWorkingDays(20);
        workation1.setRisk(Workation.RiskLevel.LOW);

        workation2 = new Workation();
        workation2.setWorkationId("W002");
        workation2.setEmployee("Jane Smith");
        workation2.setOrigin("France");
        workation2.setDestination("Italy");
        workation2.setStart(LocalDate.of(2023, 7, 1));
        workation2.setEnd(LocalDate.of(2023, 7, 15));
        workation2.setWorkingDays(10);
        workation2.setRisk(Workation.RiskLevel.HIGH);
    }

    @Test
    public void testGetAllWorkations_NoSorting() {
        // Setup mock repository
        when(workationRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(workation1, workation2));

        // Call service method
        List<WorkationDTO> result = workationService.getAllWorkations(null, null);

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("W001", result.get(0).getWorkationId());
        assertEquals("John Doe", result.get(0).getEmployee());
        assertEquals("01/06/2023", result.get(0).getStart());
        assertEquals("30/06/2023", result.get(0).getEnd());
        assertEquals("LOW", result.get(0).getRisk());
    }

    @Test
    public void testGetAllWorkations_WithSorting() {
        // Setup mock repository to return reversed order
        when(workationRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(workation2, workation1));

        // Call service method with sorting parameters
        List<WorkationDTO> result = workationService.getAllWorkations("employee", "desc");

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("W002", result.get(0).getWorkationId());
        assertEquals("Jane Smith", result.get(0).getEmployee());
        assertEquals("HIGH", result.get(0).getRisk());

        assertEquals("W001", result.get(1).getWorkationId());
        assertEquals("John Doe", result.get(1).getEmployee());
    }

    @Test
    public void testDateFormatting() {
        // Setup mock repository
        when(workationRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(workation1));

        // Call service method
        List<WorkationDTO> result = workationService.getAllWorkations(null, null);
        
        // Verify date formatting
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String expectedStartDate = workation1.getStart().format(formatter);
        String expectedEndDate = workation1.getEnd().format(formatter);
        
        assertEquals(expectedStartDate, result.get(0).getStart());
        assertEquals(expectedEndDate, result.get(0).getEnd());
    }
} 