package com.opt.github.search.repo.service;

import com.opt.github.search.repo.dto.BranchInfo;
import com.opt.github.search.repo.dto.RepositoryInfo;
import reactor.core.publisher.Flux;

public interface GithubService {
    void checkRateLimit();

    Flux<BranchInfo> getBranches(String username, String repoName);

    Flux<RepositoryInfo> getNonForkRepositories(String username);
}
