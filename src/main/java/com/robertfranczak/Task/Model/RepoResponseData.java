package com.robertfranczak.Task.Model;

import java.util.HashMap;
import java.util.Map;

public class RepoResponseData {
    private String repositoryName;
    private String ownerLogin;
    private Map<String,String> branchName = new HashMap<>();

    public RepoResponseData(String repositoryName, String ownerLogin, Map<String, String> branchName) {
        this.repositoryName = repositoryName;
        this.ownerLogin = ownerLogin;
        this.branchName = branchName;
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
