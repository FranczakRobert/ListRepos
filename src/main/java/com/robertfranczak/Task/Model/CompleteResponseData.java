package com.robertfranczak.Task.Model;

import java.util.List;

public class CompleteResponseData  {

    private final List<RepoResponseData> repoResponseData;

    public CompleteResponseData(List<RepoResponseData> repoResponseData) {
        this.repoResponseData = repoResponseData;

    }

    public List<RepoResponseData> getRepoResponseData() {
        return repoResponseData;
    }

}
