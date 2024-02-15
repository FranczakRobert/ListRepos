package com.robertfranczak.Task.Services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robertfranczak.Task.Exceptions.NotFoundException;
import com.robertfranczak.Task.Model.CompleteResponseData;
import com.robertfranczak.Task.Model.DTO.BranchDTO;
import com.robertfranczak.Task.Model.DTO.NameDTO;
import com.robertfranczak.Task.Model.RepoResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GitHubApiServiceImpl implements GitHubApiService {
    @Autowired
    private Environment env;
    private List<RepoResponseData> repoResponseData;
    private final HttpHeaders headers = new HttpHeaders();

   public GitHubApiServiceImpl() {
       headers.add("User-Agent", "IReallyWantThisJob");
       headers.add("Accept", "application/json");
   }
   @Override
    public  List<RepoResponseData> getRepositoriesDetails(String username) {
       repoResponseData = new ArrayList<>();
        CompleteResponseData completeResponseData = new CompleteResponseData(repoResponseData);
        try {
            String responseBody = getResponse(
                    env.getProperty("data.gitHubApi.url")
                    + env.getProperty("data.gitHubApi.users")
                    + username + env.getProperty("data.gitHubApi.repos"));

            fetchBranchesAndSHA(responseBody);
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
        return completeResponseData.repoResponseData();
    }

    private void fetchBranchesAndSHA(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            for (NameDTO element : objectMapper.readValue(responseBody, new TypeReference<List<NameDTO>>() {})) {
                if (element.fork().equals("false")) {
                    String result = getResponse(env.getProperty("data.gitHubApi.url")
                            + env.getProperty("data.gitHubApi.repos")
                            + "/"
                            + element.owner().login()
                            + "/"
                            + element.repositoryName()
                            + env.getProperty("data.gitHubApi.branches"));
                    processResult(result, element);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processResult(String result, NameDTO element) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<BranchDTO> obj = objectMapper.readValue(result, new TypeReference<>() {});
            createResponse(element, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createResponse(NameDTO element, List<BranchDTO> obj) {
        Map<String, String> map = new HashMap<>();
        for (BranchDTO branchDTO : obj) {
            map.put(branchDTO.name(), branchDTO.commit().sha());
        }
        this.repoResponseData.add(new RepoResponseData(element.repositoryName(), element.owner().login(), map));
    }

    private String getResponse(String apiCall) {
        HttpEntity<String> request = new HttpEntity<>("parameters", headers);
        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> responseEntity = rest.exchange(
                apiCall,
                HttpMethod.GET,
                request,
                String.class);
        return responseEntity.getBody();
    }
}
