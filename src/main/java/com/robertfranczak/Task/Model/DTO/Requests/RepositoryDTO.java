package com.robertfranczak.Task.Model.DTO.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RepositoryDTO(
        @JsonProperty("name")
        String repositoryName,
        OwnerDTO owner,
        String fork
) {
}
