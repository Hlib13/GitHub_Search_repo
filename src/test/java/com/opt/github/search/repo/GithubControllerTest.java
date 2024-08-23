package com.opt.github.search.repo;

import static org.mockito.Mockito.when;

import com.opt.github.search.repo.controllers.GithubController;
import com.opt.github.search.repo.dto.BranchInfo;
import com.opt.github.search.repo.dto.RepositoryInfo;
import com.opt.github.search.repo.service.GithubService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

@WebFluxTest(GithubController.class)
public class GithubControllerTest {
    @MockBean
    private GithubService githubService;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(new GithubController(githubService)).build();
    }

    @Test
    void testGetNonForkRepositories() {
        String username = "testuser";

        RepositoryInfo repo1 = new RepositoryInfo("Repo1", "testuser", List.of());
        RepositoryInfo repo2 = new RepositoryInfo("Repo2", "testuser", List.of());

        when(githubService.getNonForkRepositories(username)).thenReturn(Flux.just(repo1, repo2));

        webTestClient.get()
                .uri("/api/github/users/{username}/repos", username)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RepositoryInfo.class)
                .hasSize(2)
                .contains(repo1, repo2);
    }
}
