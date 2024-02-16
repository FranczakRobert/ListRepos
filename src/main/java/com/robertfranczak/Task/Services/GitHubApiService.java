package com.robertfranczak.Task.Services;

import com.robertfranczak.Task.Model.DTO.RepoResponseDataDTO;

import java.util.List;

public interface GitHubApiService {
    List<RepoResponseDataDTO> getRepositoriesDetails(String username);
}
