package com.robertfranczak.Task.Controller;


import com.robertfranczak.Task.Model.RepoResponseData;
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

    //Todo ----->  JSON Format:  Exceptionhandler : zmienic Magic numbers/Strings : Lista z branchami i SHA : dokumentacja : Testy
    @GetMapping("/{username}")
    public List<RepoResponseData> getGitHubUserDetails(
            @PathVariable String username) {
        return gitHubApiService.getRepositoriesDetails(username);
    }
}
