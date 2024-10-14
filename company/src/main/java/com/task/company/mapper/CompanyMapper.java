package com.task.company.mapper;

import com.task.company.dto.CompanyRequest;
import com.task.company.dto.CompanyResponse;
import com.task.company.dto.DepartmentResponse;
import com.task.company.model.Company;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanyMapper {

    private final DepartmentMapper departmentMapper;

    public CompanyMapper(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    public CompanyResponse toResponse(Company company) {
        List<DepartmentResponse> departmentResponses = company.getDepartments().stream()
                .map(departmentMapper::toResponse)
                .collect(Collectors.toList());

        return CompanyResponse.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .departments(departmentResponses)
                .build();
    }

    public Company toEntity(CompanyRequest companyRequest) {
        return Company.builder()
                .companyName(companyRequest.getCompanyName())
                .build();
    }
}