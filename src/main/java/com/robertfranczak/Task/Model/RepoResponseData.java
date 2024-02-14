package com.robertfranczak.Task.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;

@Data
public class RepoResponseData {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String repositoryName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ownerLogin;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> branchNameAndLastCommitSHA; // liste branchy - branch -> name - > sha
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;

    public RepoResponseData(String message, Integer status) {
        this.message = message;
        this.status = status;
    }

    public RepoResponseData(String repositoryName, String ownerLogin, Map<String, String> map) {
        this.repositoryName = repositoryName;
        this.ownerLogin = ownerLogin;
        this.branchNameAndLastCommitSHA = map;
    }
}
