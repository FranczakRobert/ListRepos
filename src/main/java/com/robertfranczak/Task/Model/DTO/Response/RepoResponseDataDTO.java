package com.robertfranczak.Task.Model.DTO.Response;

import java.util.List;

public record RepoResponseDataDTO(
     String repositoryName,
     String ownerLogin,
     List<BranchDTO> branch
){}
