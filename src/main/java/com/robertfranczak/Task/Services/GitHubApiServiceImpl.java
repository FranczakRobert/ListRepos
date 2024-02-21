package com.robertfranczak.Task.Services;

import com.robertfranczak.Task.Exceptions.NotFoundException;
import com.robertfranczak.Task.Model.Constants;
import com.robertfranczak.Task.Model.DTO.Requests.BranchRequestDTO;
import com.robertfranczak.Task.Model.DTO.Requests.NameDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GitHubApiServiceImpl implements GitHubApiService {

    private final WebClient webClient = WebClient.builder()
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .baseUrl(Constants.API_BASE_URL)
            .build();

   @Override
    public  Map<NameDTO, ResponseEntity<List<BranchRequestDTO>>> getRepositoriesDetails(String username) {
       return getListOfRepositories(username);
   }

    private  Map<NameDTO, ResponseEntity<List<BranchRequestDTO>>> getListOfRepositories(String username) {
        WebClient webClient = this.webClient;

        try {
            Mono<ResponseEntity<List<NameDTO>>> result = retrieveEntityList(webClient,Constants.API_USER + "/" + username + Constants.API_REPOS, NameDTO.class);

            Map<NameDTO, ResponseEntity<List<BranchRequestDTO>>> resultMap = result.block().getBody().stream()
                    .filter(element -> element.fork().equals("false"))
                    .map(element -> {
                        Mono<ResponseEntity<List<BranchRequestDTO>>> repositoriesDetails = retrieveEntityList(webClient,Constants.API_REPOS + "/" +element.owner().login() + "/" + element.repositoryName() + Constants.API_BRANCH,BranchRequestDTO.class);
                        return new AbstractMap.SimpleEntry<>(element, repositoriesDetails.block());
                    })
                    .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));

            return resultMap;
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (NullPointerException e) {
            e.getMessage();
        }
        return null;
   }

    private <T> Mono<ResponseEntity<List<T>>> retrieveEntityList(WebClient webClient, String uri, Class<T> responseType) {
        return webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntityList(responseType);
    }
}


