package com.task.company.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.company.dto.CompanyRequest;
import com.task.company.dto.CompanyResponse;
import com.task.company.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CompanyController.class)
class CompanyControllerTest {

    @MockBean
    private CompanyService companyService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCompany() throws Exception {
        CompanyRequest companyRequest = new CompanyRequest("test companyName");
        CompanyResponse companyResponse = new CompanyResponse(1L, "test companyName", null);

        when(companyService.createCompany(eq(companyRequest))).thenReturn(companyResponse);

        String companyJson = objectMapper.writeValueAsString(companyRequest);
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(companyJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.companyName").value("test companyName"));

        verify(companyService, times(1)).createCompany(companyRequest);
    }

    @Test
    void updateCompany() throws Exception {
        Long companyId = 1L;
        CompanyRequest updatedCompanyRequest = new CompanyRequest("Updated Company");
        CompanyResponse updatedCompanyResponse = new CompanyResponse(companyId, "Updated Company", null);

        when(companyService.updateCompany(eq(companyId), eq(updatedCompanyRequest)))
                .thenReturn(updatedCompanyResponse);

        String updatedCompanyJson = objectMapper.writeValueAsString(updatedCompanyRequest);
        mockMvc.perform(put("/companies/{id}", companyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCompanyJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.companyName").value("Updated Company"));

        verify(companyService, times(1)).updateCompany(eq(companyId), eq(updatedCompanyRequest));
    }

    @Test
    void deleteCompany() throws Exception {
        Long companyId = 1L;

        mockMvc.perform(delete("/companies/{id}", companyId))
                .andExpect(status().isNoContent());

        verify(companyService, times(1)).deleteCompany(eq(companyId));
    }

    @Test
    void getAllCompanies() throws Exception {
        List<CompanyResponse> companyResponses = Arrays.asList(
                new CompanyResponse(1L, "Company 1", null),
                new CompanyResponse(2L, "Company 2", null)
        );

        when(companyService.getAllCompanies()).thenReturn(companyResponses);

        mockMvc.perform(get("/companies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].companyName").value("Company 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].companyName").value("Company 2"));

        verify(companyService, times(1)).getAllCompanies();
    }

    @Test
    void getCompanyById() throws Exception {
        Long companyId = 1L;
        CompanyResponse companyResponse = new CompanyResponse(companyId, "Company 1", null);

        when(companyService.getCompanyById(eq(companyId))).thenReturn(companyResponse);

        mockMvc.perform(get("/companies/{id}", companyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(companyId))
                .andExpect(jsonPath("$.companyName").value("Company 1"));

        verify(companyService, times(1)).getCompanyById(eq(companyId));
    }
}
