package com.workflex.workstation.controller;

import com.workflex.workstation.dto.WorkationDTO;
import com.workflex.workstation.service.WorkationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WorkationController.class)
public class WorkationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkationService workationService;

    @Test
    public void testGetAllWorkations_NoParams() throws Exception {
        // Prepare test data
        WorkationDTO workation = createSampleWorkation();
        List<WorkationDTO> workations = Collections.singletonList(workation);
        
        // Mock service
        when(workationService.getAllWorkations(null, "asc")).thenReturn(workations);

        // Execute and verify
        mockMvc.perform(get("/workflex/workation")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].workationId").value("W001"))
                .andExpect(jsonPath("$[0].employee").value("John Doe"))
                .andExpect(jsonPath("$[0].risk").value("LOW"));
                
        verify(workationService).getAllWorkations(null, "asc");
    }

    @Test
    public void testGetAllWorkations_WithSorting() throws Exception {
        // Prepare test data
        List<WorkationDTO> workations = Arrays.asList(
            createWorkation("W001", "Alice Smith", "LOW"),
            createWorkation("W002", "Bob Jones", "HIGH")
        );
        
        // Mock service
        when(workationService.getAllWorkations("employee", "desc")).thenReturn(workations);

        // Execute and verify
        mockMvc.perform(get("/workflex/workation")
                .param("sortBy", "employee")
                .param("sortDirection", "desc")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].workationId").value("W001"))
                .andExpect(jsonPath("$[1].workationId").value("W002"));
                
        verify(workationService).getAllWorkations("employee", "desc");
    }

    @Test
    public void testGetAllWorkations_EmptyResult() throws Exception {
        // Mock service
        when(workationService.getAllWorkations(any(), any())).thenReturn(Collections.emptyList());

        // Execute and verify
        mockMvc.perform(get("/workflex/workation")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    private WorkationDTO createSampleWorkation() {
        WorkationDTO workation = new WorkationDTO();
        workation.setWorkationId("W001");
        workation.setEmployee("John Doe");
        workation.setOrigin("Germany");
        workation.setDestination("Spain");
        workation.setStart("01/06/2023");
        workation.setEnd("30/06/2023");
        workation.setWorkingDays(20);
        workation.setRisk("LOW");
        return workation;
    }
    
    private WorkationDTO createWorkation(String id, String employee, String risk) {
        WorkationDTO workation = new WorkationDTO();
        workation.setWorkationId(id);
        workation.setEmployee(employee);
        workation.setOrigin("Germany");
        workation.setDestination("Spain");
        workation.setStart("01/06/2023");
        workation.setEnd("30/06/2023");
        workation.setWorkingDays(20);
        workation.setRisk(risk);
        return workation;
    }
} 