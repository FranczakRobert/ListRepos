package com.robertfranczak.Task.Model.DTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * BranchRequestDTO is a DTO Object stores the values of name and commit.
 * This record class is annotated with @JsonIgnoreProperties(ignoreUnknown = true)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BranchRequestDTO(
     String name,
     CommitDTO commit
) {}