package com.workflex.workstation.repository;

import com.workflex.workstation.model.Workation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkationRepository extends JpaRepository<Workation, String> {
    // Additional query methods can be added here if needed
} 