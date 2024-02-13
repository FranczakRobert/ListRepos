package com.robertfranczak.Task.POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NameWrapper {
    @JsonProperty("name")
    private String repositoryName;

    @JsonProperty("owner")
    private OwnerWrapper owner;

    @JsonProperty("fork")
    private String fork;


    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public void setLogin(OwnerWrapper login) {
        this.owner = login;
    }

    public String getOwnerLogin() {
        return owner != null ? owner.getLogin() : null;
    }

    public void setFork(String fork) {
        this.fork = fork;
    }

    public String getFork() {
        return fork;
    }
}
