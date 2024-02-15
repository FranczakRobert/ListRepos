package com.robertfranczak.Task.Services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robertfranczak.Task.Exceptions.NotFoundException;
import com.robertfranczak.Task.Model.CompleteResponseData;
import com.robertfranczak.Task.Model.DTO.BranchRequestDTO;
import com.robertfranczak.Task.Model.DTO.NameDTO;
import com.robertfranczak.Task.Model.DTO.BranchDTO;
import com.robertfranczak.Task.Model.DTO.RepoResponseDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubApiServiceImpl implements GitHubApiService {
    @Autowired
    private Environment env;
    private List<RepoResponseDataDTO> repoResponseDatumDTOS;
    private final HttpHeaders headers = new HttpHeaders();

   public GitHubApiServiceImpl() {
       headers.add("User-Agent", "IReallyWantThisJob");
       headers.add("Accept", "application/json");

   }
   @Override
    public  List<RepoResponseDataDTO> getRepositoriesDetails(String username) {
       repoResponseDatumDTOS = new ArrayList<>();
        CompleteResponseData completeResponseData = new CompleteResponseData(repoResponseDatumDTOS);
        try {
            String responseBody = getResponse(
                    env.getProperty("data.gitHubApi.url")
                    + env.getProperty("data.gitHubApi.users")
                    + username + env.getProperty("data.gitHubApi.repos"));

            fetchBranchesAndSHA(responseBody);
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
        return completeResponseData.repoResponseDatumDTOS();
    }

    private void fetchBranchesAndSHA(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.readValue(responseBody, new TypeReference<List<NameDTO>>() {})
                    .stream()
                    .filter(element -> "false".equals(element.fork()))
                    .forEach(element -> {
                        String result = getResponse(env.getProperty("data.gitHubApi.url")
                                + env.getProperty("data.gitHubApi.repos")
                                + "/"
                                + element.owner().login()
                                + "/"
                                + element.repositoryName()
                                + env.getProperty("data.gitHubApi.branches"));
                        processResult(result, element);
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processResult(String result, NameDTO element) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<BranchRequestDTO> obj = objectMapper.readValue(result, new TypeReference<>() {});
            createResponse(element, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createResponse(NameDTO element, List<BranchRequestDTO> obj) {
        List<BranchDTO> tmp = new ArrayList<>();
        obj.forEach(ele -> {
                    tmp.add(new BranchDTO(ele.name(),ele.commit().sha()));
                });
        this.repoResponseDatumDTOS.add(new RepoResponseDataDTO(element.repositoryName(), element.owner().login(), tmp));
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
