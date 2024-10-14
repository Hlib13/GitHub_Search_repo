package com.task.company.service;

import com.task.company.dto.TeamRequest;
import com.task.company.dto.TeamResponse;
import com.task.company.mapper.TeamMapper;
import com.task.company.model.Team;
import com.task.company.repository.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public TeamResponse createTeam(TeamRequest teamRequest) {
        log.info("Creating new team: {}", teamRequest.getTeamName());
        Team team = teamMapper.toEntity(teamRequest);
        team = teamRepository.save(team);
        log.info("Team created: {}", team.getId());
        return teamMapper.toResponse(team);
    }

    public List<TeamResponse> getAllTeams() {
        log.info("Fetching all teams");
        List<Team> teams = teamRepository.findAll();
        return teams.stream()
                .map(teamMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TeamResponse getTeamById(Long id) {
        log.info("Fetching team with ID: {}", id);
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with ID: " + id));
        return teamMapper.toResponse(team);
    }

    public TeamResponse updateTeam(Long id, TeamRequest teamRequest) {
        log.info("Updating team with ID: {}", id);
        return teamRepository.findById(id)
                .map(existingTeam -> {
                    existingTeam.setTeamName(teamRequest.getTeamName());
                    teamRepository.save(existingTeam);
                    log.info("Team updated: {}", id);
                    return teamMapper.toResponse(existingTeam);
                })
                .orElseThrow(() -> new RuntimeException("Team not found with ID: " + id));
    }

    public void deleteTeam(Long id) {
        log.info("Deleting team with ID: {}", id);
        if (teamRepository.existsById(id)) {
            teamRepository.deleteById(id);
            log.info("Team deleted: {}", id);
        } else {
            throw new RuntimeException("Team not found with ID: " + id);
        }
    }
}
