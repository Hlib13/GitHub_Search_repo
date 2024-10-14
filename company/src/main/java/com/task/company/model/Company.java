package com.task.company.model;

import com.task.company.dto.DepartmentResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "COMPANY")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(name = "COMPANY_NAME", nullable = false)
    private String companyName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Department> departments;
}