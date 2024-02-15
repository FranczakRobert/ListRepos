package com.robertfranczak.Task.Model.DTO;

import java.util.List;

/**
 * NameDTO is a DTO Object stores the values of repositoryName, ownerLogin and branch array.
 */
public record RepoResponseDataDTO(
     String repositoryName,
     String ownerLogin,
     List<BranchDTO> branch
){}
