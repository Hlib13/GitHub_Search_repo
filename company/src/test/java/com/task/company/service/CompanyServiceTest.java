package com.task.company.service;

import com.task.company.dto.CompanyRequest;
import com.task.company.dto.CompanyResponse;
import com.task.company.exception.NotFoundException;
import com.task.company.mapper.CompanyMapper;
import com.task.company.model.Company;
import com.task.company.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CompanyMapper companyMapper;

    @InjectMocks
    private CompanyService companyService;

    @Test
    void createCompany_shouldCreateCompanySuccessfully() {
        CompanyRequest companyRequest = new CompanyRequest("test company name");
        Company company = new Company();
        company.setCompanyName(companyRequest.getCompanyName());
        CompanyResponse companyResponse = new CompanyResponse(1L, companyRequest.getCompanyName(), null);

        when(companyMapper.toEntity(companyRequest)).thenReturn(company);
        when(companyRepository.save(company)).thenReturn(company);
        when(companyMapper.toResponse(company)).thenReturn(companyResponse);

        CompanyResponse response = companyService.createCompany(companyRequest);

        verify(companyRepository, times(1)).save(company);
        assertEquals(companyResponse, response);
    }

    @Test
    void updateCompany_shouldUpdateExistingCompany() {
        Long companyId = 1L;
        CompanyRequest companyRequest = new CompanyRequest("updated company name");
        Company existingCompany = new Company();
        existingCompany.setCompanyName("old company name");

        when(companyRepository.findById(companyId)).thenReturn(Optional.of(existingCompany));

        CompanyResponse updatedCompanyResponse = new CompanyResponse(companyId, "updated company name", null);
        when(companyMapper.toResponse(existingCompany)).thenReturn(updatedCompanyResponse);

        CompanyResponse response = companyService.updateCompany(companyId, companyRequest);

        verify(companyRepository, times(1)).save(existingCompany);
        assertEquals(updatedCompanyResponse, response);
    }

    @Test
    void updateCompany_shouldThrowExceptionWhenCompanyNotFound() {
        Long companyId = 1L;
        CompanyRequest companyRequest = new CompanyRequest("updated company name");

        when(companyRepository.findById(companyId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> companyService.updateCompany(companyId, companyRequest));
    }

    @Test
    void deleteCompany_shouldDeleteExistingCompany() {
        Long companyId = 1L;

        when(companyRepository.existsById(companyId)).thenReturn(true);

        companyService.deleteCompany(companyId);

        verify(companyRepository, times(1)).deleteById(companyId);
    }

    @Test
    void deleteCompany_shouldThrowExceptionIfCompanyDoesNotExist() {
        Long companyId = 1L;

        when(companyRepository.existsById(companyId)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> companyService.deleteCompany(companyId));

        verify(companyRepository, never()).deleteById(companyId);
    }

    @Test
    void getAllCompanies_shouldReturnListOfCompanies() {
        List<Company> companies = Arrays.asList(
                new Company(1L, "Company 1", null),
                new Company(2L, "Company 2", null)
        );
        List<CompanyResponse> companyResponses = Arrays.asList(
                new CompanyResponse(1L, "Company 1", null),
                new CompanyResponse(2L, "Company 2", null)
        );

        when(companyRepository.findAll()).thenReturn(companies);
        when(companyMapper.toResponse(companies.get(0))).thenReturn(companyResponses.get(0));
        when(companyMapper.toResponse(companies.get(1))).thenReturn(companyResponses.get(1));

        List<CompanyResponse> result = companyService.getAllCompanies();

        verify(companyRepository, times(1)).findAll();
        assertEquals(2, result.size());
        assertEquals(companyResponses, result);
    }

    @Test
    void getCompanyById_shouldReturnCompany() {
        Long companyId = 1L;
        Company company = new Company(companyId, "Company 1", null);
        CompanyResponse companyResponse = new CompanyResponse(companyId, "Company 1", null);

        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));
        when(companyMapper.toResponse(company)).thenReturn(companyResponse);

        CompanyResponse result = companyService.getCompanyById(companyId);

        verify(companyRepository, times(1)).findById(companyId);
        assertEquals(companyResponse, result);
    }

    @Test
    void getCompanyById_shouldThrowExceptionIfNotFound() {
        Long companyId = 1L;

        when(companyRepository.findById(companyId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> companyService.getCompanyById(companyId));
    }
}
