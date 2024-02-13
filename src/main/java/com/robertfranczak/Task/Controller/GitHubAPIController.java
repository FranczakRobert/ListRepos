package com.robertfranczak.Task.Controller;


import com.robertfranczak.Task.Model.RepoResponseData;
import com.robertfranczak.Task.Services.GitHubApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class GitHubAPIController {
    private final GitHubApiService gitHubApiService;

    @Autowired
    public GitHubAPIController(@Autowired GitHubApiService gitHubApiService) {
        this.gitHubApiService = gitHubApiService;
    }

    @RequestMapping("/{username}")
    public List<RepoResponseData> getGitHubUserDetails(
            @PathVariable String username) {
        return gitHubApiService.getRepositoriesDetails(username);
    }
}
