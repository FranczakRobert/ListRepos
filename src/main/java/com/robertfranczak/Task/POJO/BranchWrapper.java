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
public class BranchWrapper {
    @JsonProperty("name")
    private String name;

    @JsonProperty("commit")
    private CommitWrapper commit;
}