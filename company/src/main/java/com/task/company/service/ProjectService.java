package com.task.company.service;

import com.task.company.dto.ProjectRequest;
import com.task.company.dto.ProjectResponse;
import com.task.company.mapper.ProjectMapper;
import com.task.company.model.Manager;
import com.task.company.model.Project;
import com.task.company.repository.ManagerRepository;
import com.task.company.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ManagerRepository managerRepository;
    private final ProjectMapper projectMapper;

    public ResponseEntity<Void> createProject(ProjectRequest projectRequest) {
        log.info("Creating new project: {}", projectRequest.getProjectName());
        Manager manager = projectMapper.toEntity(projectRequest.getManager());
        manager = managerRepository.save(manager);

        Project project = projectMapper.toEntity(projectRequest);
        project.setManager(manager);
        projectRepository.save(project);

        log.info("Project {} created successfully", project.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public List<ProjectResponse> getAllProjects() {
        log.info("Fetching all projects");
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(projectMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ResponseEntity<ProjectResponse> getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        ProjectResponse response = projectMapper.toResponse(project);
        return ResponseEntity.ok(response);
    }

    public ProjectResponse updateProject(Long id, ProjectRequest projectRequest) {
        log.info("Updating project with ID: {}", id);
        return projectRepository.findById(id)
                .map(existingProject -> {
                    existingProject.setProjectName(projectRequest.getProjectName());
                    existingProject.setTeamId(projectRequest.getTeamId());
                    existingProject.setManager(projectMapper.toEntity(projectRequest.getManager()));
                    projectRepository.save(existingProject);
                    log.info("Project updated: {}", id);
                    return projectMapper.toResponse(existingProject);
                })
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));
    }

    public void deleteProject(Long id) {
        log.info("Deleting project with ID: {}", id);
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            log.info("Project deleted with ID: {}", id);
        } else {
            throw new RuntimeException("Project not found with ID: " + id);
        }
    }
}
