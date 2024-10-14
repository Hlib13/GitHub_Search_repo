package com.task.company.mapper;

import com.task.company.dto.ManagerRequest;
import com.task.company.dto.ManagerResponse;
import com.task.company.model.Manager;
import com.task.company.model.Project;
import com.task.company.dto.ProjectRequest;
import com.task.company.dto.ProjectResponse;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public Project toEntity(ProjectRequest projectRequest) {
        Manager manager = toEntity(projectRequest.getManager());
        return Project.builder()
                .projectName(projectRequest.getProjectName())
                .teamId(projectRequest.getTeamId())
                .manager(manager)
                .build();
    }

    public ProjectResponse toResponse(Project project) {
        return ProjectResponse.builder()
                .projectId(project.getId())
                .projectName(project.getProjectName())
                .teamId(project.getTeamId())
                .manager(toManagerResponse(project.getManager()))
                .build();
    }

    public Manager toEntity(ManagerRequest managerRequest) {
        return Manager.builder()
                .managerName(managerRequest.getManagerName())
                .email(managerRequest.getEmail())
                .build();
    }

    private ManagerResponse toManagerResponse(Manager manager) {
        if (manager == null) {
            return null;
        }
        return ManagerResponse.builder()
                .managerId(manager.getId())
                .managerName(manager.getManagerName())
                .email(manager.getEmail())
                .build();
    }
}
