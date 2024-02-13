package com.robertfranczak.Task.POJO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchWrapper {
    @JsonProperty("name")
    private String name;

    @JsonProperty("commit")
    private CommitWrapper commit;

    public String getRepositoryName() {
        return name;
    }

    public void setRepositoryName(String repositoryName) {
        this.name = repositoryName;
    }

    public CommitWrapper getCommitWrapper() {
        return commit;
    }

    public void setCommitWrapper(CommitWrapper commit) {
        this.commit = commit;
    }
}