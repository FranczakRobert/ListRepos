package com.robertfranczak.Task.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;

@Data
@Setter
@Getter
public class RepoResponseData {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String repositoryName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ownerLogin;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> branchName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;

    public RepoResponseData( String message, int status) {
        this.message = message;
        this.status = status;
    }

    public RepoResponseData(String repositoryName, String ownerLogin, Map<String, String> map) {
        this.repositoryName = repositoryName;
        this.ownerLogin = ownerLogin;
        this.branchName = map;
    }
}
