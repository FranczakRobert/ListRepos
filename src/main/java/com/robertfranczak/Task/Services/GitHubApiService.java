package com.robertfranczak.Task.Services;

import com.robertfranczak.Task.Model.DTO.Requests.BranchRequestDTO;
import com.robertfranczak.Task.Model.DTO.Requests.NameDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface GitHubApiService {
    Map<NameDTO, ResponseEntity<List<BranchRequestDTO>>> getRepositoriesDetails(String username);
}
