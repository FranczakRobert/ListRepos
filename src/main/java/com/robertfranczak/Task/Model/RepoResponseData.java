package com.robertfranczak.Task.Model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

public class RepoResponseData {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String repositoryName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ownerLogin;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String,String> branchName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;

    public RepoResponseData(String repositoryName, String ownerLogin, Map<String, String> branchName) {
        this.repositoryName = repositoryName;
        this.ownerLogin = ownerLogin;
        this.branchName = branchName;
    }

    public RepoResponseData(String message, Integer status) {
        this.status = status;
        this.message = message;

    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public Map<String, String> getBranchName() {
        return branchName;
    }

    public void setBranchName(Map<String, String> branchName) {
        this.branchName = branchName;
    }
}
