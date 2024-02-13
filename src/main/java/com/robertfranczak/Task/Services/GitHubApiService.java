package com.robertfranczak.Task.Services;

import com.robertfranczak.Task.Model.RepoResponseData;

import java.util.List;

public interface GitHubApiService {
    List<RepoResponseData> getRepositoriesDetails(String username);
}
