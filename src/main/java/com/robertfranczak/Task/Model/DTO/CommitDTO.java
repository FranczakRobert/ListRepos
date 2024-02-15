package com.robertfranczak.Task.Model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CommitDTO is a DTO Object stores the value of sha.
 * This record class is annotated with @JsonIgnoreProperties(ignoreUnknown = true)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CommitDTO(
        @JsonProperty("sha")
        String sha
) {}
