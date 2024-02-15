package com.robertfranczak.Task.Model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * NameDTO is a DTO Object stores the values of repositoryName, owner and fork.
 * This record class is annotated with @JsonIgnoreProperties(ignoreUnknown = true)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record NameDTO(
        @JsonProperty("name")
     String repositoryName,
     OwnerDTO owner,
     String fork
){}
