package com.opt.github.search.repo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubBranch(
        @JsonProperty("name") String name,
        @JsonProperty("commit") Commit commit
) {

    public static record Commit(@JsonProperty("sha") String sha) {}
}
