package com.workflex.workstation.service;

import com.workflex.workstation.dto.WorkationDTO;
import com.workflex.workstation.model.Workation;
import com.workflex.workstation.repository.WorkationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkationService {

    private final WorkationRepository workationRepository;

    @Transactional(readOnly = true)
    public List<WorkationDTO> getAllWorkations(String sortBy, String sortDirection) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortDirection != null && sortDirection.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }

        Sort sort = Sort.unsorted();
        if (sortBy != null && !sortBy.isEmpty()) {
            sort = Sort.by(direction, sortBy);
        }

        List<Workation> workations = workationRepository.findAll(sort);
        return workations.stream()
                .map(WorkationDTO::fromEntity)
                .collect(Collectors.toList());
    }
} 