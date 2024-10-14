package com.task.company.mapper;

import com.task.company.dto.TeamRequest;
import com.task.company.dto.TeamResponse;
import com.task.company.model.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {

    public Team toEntity(TeamRequest teamRequest) {
        return Team.builder()
                .teamName(teamRequest.getTeamName())
                .departmentId(teamRequest.getDepartmentId())
                .build();
    }

    public TeamResponse toResponse(Team team) {
        return TeamResponse.builder()
                .teamId(team.getId())
                .teamName(team.getTeamName())
                .departmentId(team.getDepartmentId())
                .build();
    }
}
