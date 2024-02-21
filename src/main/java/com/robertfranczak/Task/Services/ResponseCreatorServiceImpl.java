package com.robertfranczak.Task.Services;

import com.robertfranczak.Task.Model.DTO.Requests.BranchRequestDTO;
import com.robertfranczak.Task.Model.DTO.Requests.NameDTO;
import com.robertfranczak.Task.Model.DTO.Response.BranchDTO;
import com.robertfranczak.Task.Model.DTO.Response.RepoResponseDataDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ResponseCreatorServiceImpl implements ResponseCreatorService {
    private List<RepoResponseDataDTO> responseDTO;

    public List<RepoResponseDataDTO> createResponse(Map<NameDTO, ResponseEntity<List<BranchRequestDTO>>> map) {
        responseDTO = new ArrayList<>();

        for(Map.Entry<NameDTO,ResponseEntity<List<BranchRequestDTO>>> record : map.entrySet()) {
            packResponse(record.getKey(), Objects.requireNonNull(record.getValue().getBody()));
        }
        return responseDTO;
    }

    @Override
    public void packResponse(NameDTO repositoryDetails, List<BranchRequestDTO> branchDetails) {
        List<BranchDTO> branchDTOList = new ArrayList<>();
        branchDetails.forEach(ele -> {
            branchDTOList.add(new BranchDTO(ele.name(),ele.commit().sha()));
        });

        responseDTO.add(new RepoResponseDataDTO(
                repositoryDetails.repositoryName(),
                repositoryDetails.owner().login(),
                branchDTOList
        ));
    }
}
