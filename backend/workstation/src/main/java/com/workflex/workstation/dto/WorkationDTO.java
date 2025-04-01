package com.workflex.workstation.dto;

import com.workflex.workstation.model.Workation;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class WorkationDTO {
    private String workationId;
    private String employee;
    private String origin;
    private String destination;
    private String start;
    private String end;
    private Integer workingDays;
    private String risk;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static WorkationDTO fromEntity(Workation workation) {
        WorkationDTO dto = new WorkationDTO();
        dto.setWorkationId(workation.getWorkationId());
        dto.setEmployee(workation.getEmployee());
        dto.setOrigin(workation.getOrigin());
        dto.setDestination(workation.getDestination());
        dto.setStart(workation.getStart().format(DATE_FORMATTER));
        dto.setEnd(workation.getEnd().format(DATE_FORMATTER));
        dto.setWorkingDays(workation.getWorkingDays());
        dto.setRisk(workation.getRisk().toString());
        return dto;
    }
} 