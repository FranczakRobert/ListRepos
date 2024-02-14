package com.robertfranczak.Task.Services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robertfranczak.Task.Model.ApiException;
import com.robertfranczak.Task.Model.CompleteResponseData;
import com.robertfranczak.Task.Model.RepoResponseData;
import com.robertfranczak.Task.Model.DTO.BranchDTO;
import com.robertfranczak.Task.Model.DTO.NameDTO;
import com.robertfranczak.Task.Utils.GitHubErrorParser;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GitHubApiServiceImpl implements GitHubApiService {
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

        HttpEntity<String> request = new HttpEntity<>("parameters", headers);

        RestTemplate rest = new RestTemplate();

        try {
            ResponseEntity<String> responseEntity = rest.exchange(
                    "https://api.github.com/users/" + username + "/repos",
                    HttpMethod.GET,
                    request,
                    String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK)
                fetchBranchesAndSHA(responseEntity);

        } catch (Exception e) {
            ApiException error = GitHubErrorParser.getInstance().extractStatusAndMessageError(e.getMessage());
            repoResponseData.add(new RepoResponseData(error.message(),error.status()));
        }

        return completeResponseData.repoResponseData();
    }

    private void fetchBranchesAndSHA(ResponseEntity<String> responseEntity) {
        String responseBody = responseEntity.getBody();

        HttpEntity<String> request = new HttpEntity<>("parameters", headers);

        RestTemplate rest = new RestTemplate();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            for (NameDTO element : objectMapper.readValue(responseBody, new TypeReference<List<NameDTO>>() {
            })) {
                System.out.println("https://api.github.com/repos/" + element.owner().login() + "/" + element.repositoryName() + "/branches");
                if (element.fork().equals("false"))
                    responseEntity = rest.exchange(
                            "https://api.github.com/repos/" + element.owner().login() + "/" + element.repositoryName() + "/branches",
                            HttpMethod.GET,
                            request,
                            String.class);

                String result = responseEntity.getBody();
                objectMapper = new ObjectMapper();

                try {
                    if (responseEntity.getStatusCode() == HttpStatus.OK) {
                        List<BranchDTO> obj = objectMapper.readValue(result, new TypeReference<>() {
                        });

                        createResponse(element, obj);
                    }
                } catch (Exception e) {
                    //IMPLEMENT
                    e.printStackTrace();
                }
            }
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

}
