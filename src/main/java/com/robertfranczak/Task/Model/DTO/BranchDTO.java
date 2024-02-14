package com.robertfranczak.Task.Model.DTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BranchDTO(
     String name,
     CommitDTO commit
) {}