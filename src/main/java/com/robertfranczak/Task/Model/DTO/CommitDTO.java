package com.robertfranczak.Task.Model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public record CommitDTO(
        @JsonProperty("sha")
        String sha
) {}
