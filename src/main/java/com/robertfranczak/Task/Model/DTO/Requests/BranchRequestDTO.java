package com.robertfranczak.Task.Model.DTO.Requests;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BranchRequestDTO(
     String name,
     CommitDTO commit
) {}