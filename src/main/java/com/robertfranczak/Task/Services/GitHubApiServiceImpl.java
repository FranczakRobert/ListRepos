package com.robertfranczak.Task.Services;

import com.robertfranczak.Task.Model.RepoResponseData;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubApiServiceImpl implements GitHubApiService {
    private final List<RepoResponseData> repoResponseData = new ArrayList<>();

   private final HttpHeaders headers = new HttpHeaders();

   public GitHubApiServiceImpl() {
       headers.add("User-Agent", "IReallyWantThisJob");
       headers.add("Accept", "application/json");
   }

    @Override
    public List<RepoResponseData> getRepositoriesDetails(String username) {

        HttpEntity<String> request = new HttpEntity<>("parameters", headers);
        RestTemplate rest = new RestTemplate();

        ResponseEntity<String> responseEntity = rest.exchange(
                "https://api.github.com/users/" + username + "/repos",
                HttpMethod.GET,
                request,
                String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK)
            fetchBranchesAndSHA(responseEntity);

        return repoResponseData;
    }

    private void fetchBranchesAndSHA(ResponseEntity<String> responseEntity) {
        String responseBody = responseEntity.getBody();

        HttpEntity<String> request = new HttpEntity<>("parameters", headers);
        RestTemplate rest = new RestTemplate();

    }



}
