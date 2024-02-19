package com.robertfranczak.Task.Model.DTO.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public record BranchDTO(
        String name,
       String lastSha
){}
