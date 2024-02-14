package com.robertfranczak.Task.POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NameWrapper {
    @JsonProperty("name")
    private String repositoryName;

    @JsonProperty("owner")
    private OwnerWrapper owner;

    @JsonProperty("fork")
    private String fork;
}
