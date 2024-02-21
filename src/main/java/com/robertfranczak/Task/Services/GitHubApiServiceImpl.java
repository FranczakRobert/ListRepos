package com.robertfranczak.Task.Services;

import com.robertfranczak.Task.Exceptions.NotFoundException;
import com.robertfranczak.Task.Model.Constants;
import com.robertfranczak.Task.Model.DTO.Requests.BranchRequestDTO;
import com.robertfranczak.Task.Model.DTO.Requests.NameDTO;
import com.robertfranczak.Task.Utils.UriStringBuilder;
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
       WebClient webClient = this.webClient;
       try {
           UriStringBuilder.builder.append(Constants.API_USER + "/").append(username).append(Constants.API_REPOS);
           Mono<ResponseEntity<List<NameDTO>>> result = retrieveEntityList(webClient,UriStringBuilder.builder.toString(), NameDTO.class);

           Map<NameDTO, ResponseEntity<List<BranchRequestDTO>>> resultMap = result.block().getBody().stream()
                   .filter(element -> element.fork().equals("false"))
                   .map(element -> {
                       UriStringBuilder.builder.delete(0, UriStringBuilder.builder.length());
                       UriStringBuilder.builder.append(Constants.API_REPOS + "/").append(element.owner().login()).append("/").append(element.repositoryName()).append(Constants.API_BRANCH);
                       Mono<ResponseEntity<List<BranchRequestDTO>>> repositoriesDetails = retrieveEntityList(webClient,UriStringBuilder.builder.toString(), BranchRequestDTO.class);
                       return new AbstractMap.SimpleEntry<>(element, repositoriesDetails.block());
                   })
                   .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));

           return resultMap;
       } catch (NotFoundException e) {
           throw new NotFoundException(e.getMessage());
       }
   }

    private <T> Mono<ResponseEntity<List<T>>> retrieveEntityList(WebClient webClient, String uri, Class<T> responseType) {
        return webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntityList(responseType);
    }
}


