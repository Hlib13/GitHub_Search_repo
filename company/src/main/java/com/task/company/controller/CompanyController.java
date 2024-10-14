package com.task.company.controller;

import com.task.company.dto.CompanyRequest;
import com.task.company.dto.CompanyResponse;
import com.task.company.service.CompanyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
@AllArgsConstructor
@Slf4j
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(@RequestBody CompanyRequest companyRequest) {
        log.info("Creating a new company");
        CompanyResponse companyResponse = companyService.createCompany(companyRequest);
        return ResponseEntity.status(201).body(companyResponse);
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompanies() {
        log.info("Fetching all companies");
        List<CompanyResponse> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable Long id) {
        log.info("Fetching company with ID: {}", id);
        CompanyResponse company = companyService.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(@PathVariable Long id, @RequestBody CompanyRequest companyRequest) {
        log.info("Updating company with ID: {}", id);
        CompanyResponse updatedCompany = companyService.updateCompany(id, companyRequest);
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        log.info("Deleting company with ID: {}", id);
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }

}
