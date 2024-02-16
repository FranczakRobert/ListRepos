package com.robertfranczak.Task.Services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robertfranczak.Task.Exceptions.NotFoundException;
import com.robertfranczak.Task.Model.CompleteResponseData;
import com.robertfranczak.Task.Model.Constants;
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
    private final HttpHeaders headers;

   public GitHubApiServiceImpl() {
       headers = new HttpHeaders();
       headers.add(Constants.HEADER_AGENT, Constants.HEADER_AGENT_VALUE);
       headers.add(Constants.HEADER_ACCEPT, Constants.HEADER_VALUE_JSON);
   }

   @Override
    public  List<RepoResponseDataDTO> getRepositoriesDetails(String username) {
       repoResponseDatumDTOS = new ArrayList<>();
        CompleteResponseData completeResponseData = new CompleteResponseData(repoResponseDatumDTOS);
        try {
            String responseBody = getResponse(
                    env.getProperty(Constants.API_URL)
                    + env.getProperty(Constants.API_USER)
                    + username + env.getProperty(Constants.API_REPOS));

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
                        String result = getResponse(env.getProperty(Constants.API_URL)
                                + env.getProperty(Constants.API_REPOS)
                                + "/"
                                + element.owner().login()
                                + "/"
                                + element.repositoryName()
                                + env.getProperty(Constants.API_BRANCH));
                        processResult(result, element);
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getResponse(String apiCall) {
        HttpEntity<String> request = new HttpEntity<>(Constants.HEADER_PARAM, headers);
        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> responseEntity = rest.exchange(
                apiCall,
                HttpMethod.GET,
                request,
                String.class);
        return responseEntity.getBody();
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

    private void createResponse(NameDTO element, List<BranchRequestDTO> branchRequestDTO) {
        List<BranchDTO> branchDTOList = new ArrayList<>();
        branchRequestDTO.forEach(ele -> {
            branchDTOList.add(new BranchDTO(ele.name(),ele.commit().sha()));
        });
        this.repoResponseDatumDTOS.add(new RepoResponseDataDTO(element.repositoryName(), element.owner().login(), branchDTOList));

    }
}
