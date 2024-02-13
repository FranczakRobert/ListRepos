package com.robertfranczak.Task.Model;

import org.springframework.http.HttpStatusCode;

import java.util.ArrayList;
import java.util.List;

public class CompleteResponseData {
    private List<RepoResponseData> repoResponseData = new ArrayList<>();
    private String msg;
    private HttpStatusCode statusCode;


    public CompleteResponseData(List<RepoResponseData> repoResponseData, String msg, HttpStatusCode statusCode) {
        this.repoResponseData = repoResponseData;
        this.msg = msg;
        this.statusCode = statusCode;
    }

    public List<RepoResponseData> getRepoResponseData() {
        return repoResponseData;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
