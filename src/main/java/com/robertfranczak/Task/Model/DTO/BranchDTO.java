package com.robertfranczak.Task.Model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * BranchDTO is a DTO Object stores the values of name and lastSha.
 * This record class is annotated with @JsonIgnoreProperties(ignoreUnknown = true)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BranchDTO(
        String name,
       String lastSha
){}
