package com.robertfranczak.Task.Services;

import com.robertfranczak.Task.Model.DTO.RepoResponseDataDTO;

import java.util.List;

/**
 * GitHubApiService is an interface for interacting with the GitHub API to retrieve repository details.
 */
public interface GitHubApiService {

    /**
     * Retrieves details of repositories for a given GitHub user.
     * @param username The username of the GitHub user whose repositories details are to be retrieved.
     * @return A list of RepoResponseDataDTO objects representing the repositories details.
     */
    List<RepoResponseDataDTO> getRepositoriesDetails(String username);
}
