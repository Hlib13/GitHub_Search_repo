package com.task.company.mapper;

import com.task.company.dto.DepartmentRequest;
import com.task.company.dto.DepartmentResponse;
import com.task.company.model.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public Department toEntity(DepartmentRequest departmentRequest) {
        return Department.builder()
                .departmentName(departmentRequest.getDepartmentName())
                .companyId(departmentRequest.getCompanyId())
                .build();
    }

    public DepartmentResponse toResponse(Department department) {
        return DepartmentResponse.builder()
                .departmentId(department.getId())
                .departmentName(department.getDepartmentName())
                .companyId(department.getCompanyId())
                .build();
    }
}
