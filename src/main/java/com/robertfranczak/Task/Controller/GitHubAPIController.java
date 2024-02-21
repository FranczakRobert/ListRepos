package com.robertfranczak.Task.Controller;


import com.robertfranczak.Task.Model.DTO.Requests.BranchRequestDTO;
import com.robertfranczak.Task.Model.DTO.Requests.NameDTO;
import com.robertfranczak.Task.Model.DTO.Response.RepoResponseDataDTO;
import com.robertfranczak.Task.Services.GitHubApiService;
import com.robertfranczak.Task.Services.ResponseCreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class GitHubAPIController {

    private final GitHubApiService gitHubApiService;

    private final ResponseCreatorService responseCreatorService;
    @GetMapping(value = "/{username}")
    public List<RepoResponseDataDTO> getGitHubUserDetails(@PathVariable String username) {

        Map<NameDTO, ResponseEntity<List<BranchRequestDTO>>> map = gitHubApiService.getRepositoriesDetails(username);

        return responseCreatorService.createResponse(map);
    }
}
