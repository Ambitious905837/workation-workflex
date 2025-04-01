package com.workflex.workstation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "workations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Workation {

    @Id
    private String workationId;
    private String employee;
    private String origin;
    private String destination;
    
    @Column(name = "\"start\"")
    private LocalDate start;
    
    @Column(name = "\"end\"")
    private LocalDate end;
    
    private Integer workingDays;
    
    @Enumerated(EnumType.STRING)
    private RiskLevel risk;

    public enum RiskLevel {
        HIGH, 
        LOW, 
        NO
    }
} 