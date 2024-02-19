package com.robertfranczak.Task.Controller;


import com.robertfranczak.Task.Model.DTO.Response.RepoResponseDataDTO;
import com.robertfranczak.Task.Services.GitHubApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class GitHubAPIController {

    private final GitHubApiService gitHubApiService;
    @GetMapping(value = "/{username}")
    public List<RepoResponseDataDTO> getGitHubUserDetails(@PathVariable String username) {

        return gitHubApiService.getRepositoriesDetails(username);
    }
}
