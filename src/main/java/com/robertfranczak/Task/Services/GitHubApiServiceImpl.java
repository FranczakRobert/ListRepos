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

/**
 * GitHubApiServiceImpl is a service class implementing the GitHubApiService interface.
 * It is responsible for retrieving repository details from the GitHub API.
 */
@Service
public class GitHubApiServiceImpl implements GitHubApiService {
    @Autowired
    private Environment env;
    private List<RepoResponseDataDTO> repoResponseDatumDTOS;
    private final HttpHeaders headers;

    /**
     * Constructs a new GitHubApiServiceImpl and sets up the necessary HTTP headers.
     */
   public GitHubApiServiceImpl() {
       headers = new HttpHeaders();
       headers.add(Constants.HEADER_AGENT, Constants.HEADER_AGENT_VALUE);
       headers.add(Constants.HEADER_ACCEPT, Constants.HEADER_VALUE_JSON);
   }

    /**
     * Retrieves details of repositories for a given GitHub user.
     *
     * @param username The username of the GitHub user whose repositories details are to be retrieved.
     * @return A list of RepoResponseDataDTO objects representing the repositories details.
     * @throws NotFoundException if an error occurs during the retrieval process.
     */
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

    /**
     * Fetches branch details and corresponding commit SHAs for each repository.
     *
     * @param responseBody The response body containing repository details in JSON format.
     */
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

    /**
     * Processes the result of fetching branch details and creates the response data.
     *
     * @param result The response containing branch details in JSON format.
     * @param element The repository details represented by NameDTO.
     */
    private void processResult(String result, NameDTO element) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<BranchRequestDTO> obj = objectMapper.readValue(result, new TypeReference<>() {});
            createResponse(element, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the response data containing repository name, owner login, and branch details, representing by RepoResponseDataDTO
     * @param element The repository details.
     * @param branchRequestDTO The list of branch details.
     */
    private void createResponse(NameDTO element, List<BranchRequestDTO> branchRequestDTO) {
        List<BranchDTO> branchDTOList = new ArrayList<>();
        branchRequestDTO.forEach(ele -> {
            branchDTOList.add(new BranchDTO(ele.name(),ele.commit().sha()));
        });
        this.repoResponseDatumDTOS.add(new RepoResponseDataDTO(element.repositoryName(), element.owner().login(), branchDTOList));

    }
}
