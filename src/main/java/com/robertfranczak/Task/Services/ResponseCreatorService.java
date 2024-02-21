package com.robertfranczak.Task.Services;

import com.robertfranczak.Task.Model.DTO.Requests.BranchRequestDTO;
import com.robertfranczak.Task.Model.DTO.Requests.NameDTO;
import com.robertfranczak.Task.Model.DTO.Response.RepoResponseDataDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ResponseCreatorService {
    List<RepoResponseDataDTO> createResponse(Map<NameDTO, ResponseEntity<List<BranchRequestDTO>>> map);
    void packResponse(NameDTO repositoryDetails, List<BranchRequestDTO> branchDetails);
}
