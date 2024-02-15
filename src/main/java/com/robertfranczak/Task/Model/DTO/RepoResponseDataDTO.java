package com.robertfranczak.Task.Model.DTO;

import java.util.List;


public record RepoResponseDataDTO(
     String repositoryName,
     String ownerLogin,
     List<BranchDTO> branch
){}
