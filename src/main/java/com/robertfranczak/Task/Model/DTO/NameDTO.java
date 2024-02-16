package com.robertfranczak.Task.Model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NameDTO(
        @JsonProperty("name")
     String repositoryName,
     OwnerDTO owner,
     String fork
){}
