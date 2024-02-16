package com.robertfranczak.Task.Controller;


import com.robertfranczak.Task.Exceptions.NotAcceptableHeader;
import com.robertfranczak.Task.Model.DTO.RepoResponseDataDTO;
import com.robertfranczak.Task.Services.GitHubApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller handling GitHub API requests.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class GitHubAPIController {

    private final GitHubApiService gitHubApiService;
    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RepoResponseDataDTO> getGitHubUserDetails(@PathVariable String username, @RequestHeader(value = "Accept") String acceptHeader) {
        if (acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE)) {
            return gitHubApiService.getRepositoriesDetails(username);
        } else {
            throw new NotAcceptableHeader("Not Acceptable Header");
        }
    }
}
