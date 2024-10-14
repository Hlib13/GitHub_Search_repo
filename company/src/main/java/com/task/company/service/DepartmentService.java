package com.task.company.service;


import com.task.company.dto.DepartmentRequest;
import com.task.company.dto.DepartmentResponse;
import com.task.company.mapper.DepartmentMapper;
import com.task.company.model.Department;
import com.task.company.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentResponse createDepartment(DepartmentRequest departmentRequest) {
        log.info("Creating new department: {}", departmentRequest.getDepartmentName());
        Department department = departmentMapper.toEntity(departmentRequest);
        department = departmentRepository.save(department);
        log.info("Department created: {}", department.getId());
        return departmentMapper.toResponse(department);
    }

    public List<DepartmentResponse> getAllDepartments() {
        log.info("Fetching all departments");
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(departmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    public DepartmentResponse getDepartmentById(Long id) {
        log.info("Fetching department with ID: {}", id);
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));
        return departmentMapper.toResponse(department);
    }

    public DepartmentResponse updateDepartment(Long id, DepartmentRequest departmentRequest) {
        log.info("Updating department with ID: {}", id);
        return departmentRepository.findById(id)
                .map(existingDepartment -> {
                    existingDepartment.setDepartmentName(departmentRequest.getDepartmentName());
                    departmentRepository.save(existingDepartment);
                    log.info("Department updated: {}", id);
                    return departmentMapper.toResponse(existingDepartment);
                })
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));
    }

    public void deleteDepartment(Long id) {
        log.info("Deleting department with ID: {}", id);
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
            log.info("Department deleted: {}", id);
        } else {
            throw new RuntimeException("Department not found with ID: " + id);
        }
    }
}
