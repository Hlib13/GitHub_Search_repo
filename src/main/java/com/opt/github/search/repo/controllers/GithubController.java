package com.opt.github.search.repo.controllers;

import com.opt.github.search.repo.dto.BranchInfo;
import com.opt.github.search.repo.dto.RepositoryInfo;
import com.opt.github.search.repo.service.GithubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/github")
@RequiredArgsConstructor
@Tag(name = "GitHub API", description = "Operations related to GitHub repositories and branches")
public class GithubController {
    private final GithubService githubService;

    @Operation(summary = "Get Non-Fork Repositories", description = "Retrieve all non-fork repositories for a given GitHub username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved non-fork repositories"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Rate limit exceeded or access forbidden")
    })
    @GetMapping(value = "/users/{username}/repos", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<RepositoryInfo> getNonForkRepositories(@PathVariable String username) {
        return githubService.getNonForkRepositories(username);
    }
}
