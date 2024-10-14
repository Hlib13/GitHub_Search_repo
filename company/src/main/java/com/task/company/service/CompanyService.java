package com.task.company.service;

import com.task.company.dto.CompanyRequest;
import com.task.company.dto.CompanyResponse;
import com.task.company.exception.NotFoundException;
import com.task.company.mapper.CompanyMapper;
import com.task.company.model.Company;
import com.task.company.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyResponse createCompany(CompanyRequest companyRequest) {
        log.info("Creating new company: {}", companyRequest.getCompanyName());
        Company company = companyMapper.toEntity(companyRequest);
        company = companyRepository.save(company);
        log.info("Company created: {}", company.getId());
        return companyMapper.toResponse(company);
    }

    public List<CompanyResponse> getAllCompanies() {
        log.info("Fetching all companies");
        List<Company> companies = companyRepository.findAll();
        return companies.stream()
                .map(companyMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CompanyResponse getCompanyById(Long id) {
        log.info("Fetching company with ID: {}", id);
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Company not found with ID: " + id));
        return companyMapper.toResponse(company);
    }

    public CompanyResponse updateCompany(Long id, CompanyRequest companyRequest) {
        log.info("Updating company with ID: {}", id);
        return companyRepository.findById(id)
                .map(existingCompany -> {
                    existingCompany.setCompanyName(companyRequest.getCompanyName());
                    companyRepository.save(existingCompany);
                    log.info("Company updated: {}", id);
                    return companyMapper.toResponse(existingCompany);
                })
                .orElseThrow(() -> new NotFoundException("Company not found with ID: " + id));
    }

    public void deleteCompany(Long id) {
        log.info("Deleting company with ID: {}", id);
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            log.info("Company deleted: {}", id);
        } else {
            throw new NotFoundException("Company not found with ID: " + id);
        }
    }
}
