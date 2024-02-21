package com.robertfranczak.Task.Services;

import com.robertfranczak.Task.Exceptions.NotFoundException;
import com.robertfranczak.Task.Model.Constants;
import com.robertfranczak.Task.Model.DTO.Requests.BranchRequestDTO;
import com.robertfranczak.Task.Model.DTO.Requests.NameDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
            Mono<ResponseEntity<List<NameDTO>>> result = webClient.get()
                    .uri(Constants.API_USER + "/{username}" + Constants.API_REPOS , username)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .onStatus(httpStatusCode -> httpStatusCode == HttpStatus.NOT_FOUND,
                            error -> Mono.error(new NotFoundException("Not Found")))
                    .toEntityList(NameDTO.class);

            List<NameDTO> response = Objects.requireNonNull(result.block()).getBody();
            return getBranchDetails(response);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (NullPointerException e) {
            e.getMessage();
        }
        return null;
   }

   private  Map<NameDTO, ResponseEntity<List<BranchRequestDTO>>> getBranchDetails(List<NameDTO> repositories) {
       WebClient webClient = this.webClient;

       Map<NameDTO, ResponseEntity<List<BranchRequestDTO>>> resultMap = repositories.stream()
               .filter(element -> element.fork().equals("false"))
               .map(element -> {
                   Mono<ResponseEntity<List<BranchRequestDTO>>> repositoriesDetails =
                           webClient.get()
                                   .uri(Constants.API_REPOS + "/{username}/{repositoryName}" + Constants.API_BRANCH, element.owner().login(), element.repositoryName())
                                   .accept(MediaType.APPLICATION_JSON)
                                   .retrieve()
                                   .toEntityList(BranchRequestDTO.class);

                   return new AbstractMap.SimpleEntry<>(element, repositoriesDetails.block());
               })
               .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));

       return resultMap;
   }
}


