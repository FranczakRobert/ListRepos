package com.robertfranczak.Task.Model;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class Constants {
    public static final String API_BASE_URL = "https://api.github.com";
    public static final String API_USER = "/users";
    public static final String API_REPOS = "/repos";
    public static final String API_BRANCH = "/branches";

    public static final WebClient webClient = WebClient.builder()
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .baseUrl(Constants.API_BASE_URL)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();

}
