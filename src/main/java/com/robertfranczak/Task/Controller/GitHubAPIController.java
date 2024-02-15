package com.robertfranczak.Task.Controller;


import com.robertfranczak.Task.Model.DTO.RepoResponseDataDTO;
import com.robertfranczak.Task.Services.GitHubApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller handling GitHub API requests.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class GitHubAPIController {

    private final GitHubApiService gitHubApiService;

    //Todo ----->  JSON Format: dokumentacja : Testy

    /**
     * Get GitHub repository details about particular user.
     *
     * @param username GitHub username
     * @return List of repository details for the given user representing by RepoResponseDataDTO.
     */
    @GetMapping("/{username}")
    public List<RepoResponseDataDTO> getGitHubUserDetails(
            @PathVariable String username) {
        return gitHubApiService.getRepositoriesDetails(username);
    }
}
