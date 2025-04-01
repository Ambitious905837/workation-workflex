package com.workflex.workstation.controller;

import com.workflex.workstation.dto.WorkationDTO;
import com.workflex.workstation.service.WorkationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/workflex/workation")
@RequiredArgsConstructor
public class WorkationController {

    private final WorkationService workationService;

    @GetMapping
    public ResponseEntity<List<WorkationDTO>> getAllWorkations(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        
        List<WorkationDTO> workations = workationService.getAllWorkations(sortBy, sortDirection);
        return ResponseEntity.ok(workations);
    }
} 