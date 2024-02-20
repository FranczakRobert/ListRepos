package com.robertfranczak.Task.Services;

import com.robertfranczak.Task.Exceptions.NotFoundException;
import com.robertfranczak.Task.Model.Constants;
import com.robertfranczak.Task.Model.DTO.Requests.BranchRequestDTO;
import com.robertfranczak.Task.Model.DTO.Requests.NameDTO;
import com.robertfranczak.Task.Model.DTO.Response.BranchDTO;
import com.robertfranczak.Task.Model.DTO.Response.RepoResponseDataDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GitHubApiServiceImpl implements GitHubApiService {
    private List<RepoResponseDataDTO> responseDTO;

   @Override
    public List<RepoResponseDataDTO> getRepositoriesDetails(String username) {
       responseDTO = new ArrayList<>();

       getListOfRepositories(username);
       return responseDTO;
   }

   private void getListOfRepositories(String username) {
       WebClient webClient = Constants.webClient;
       try {
           Mono<ResponseEntity<List<NameDTO>>> result = webClient.get()
                   .uri(Constants.API_USER + "/{username}" + Constants.API_REPOS , username)
                   .accept(MediaType.APPLICATION_JSON)
                   .retrieve()
                   .onStatus(httpStatusCode -> httpStatusCode.value() == HttpStatus.NOT_FOUND.value(),
                           error -> Mono.error(new NotFoundException("Not Found")))
                   .toEntityList(NameDTO.class);

           List<NameDTO> response = Objects.requireNonNull(result.block()).getBody();
           getBranchDetails(response);
       } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
       }
   }

   private void getBranchDetails(List<NameDTO> repositories) {

       WebClient webClient = Constants.webClient;
       for (NameDTO element : repositories) {
           if(element.fork().equals("false")) {
               Mono<ResponseEntity<List<BranchRequestDTO>>> repositoriesDetails =
                       webClient.get()
                               .uri(Constants.API_REPOS + "/{username}/{repositoryName}" + Constants.API_BRANCH,element.owner().login(),element.repositoryName())
                               .accept(MediaType.APPLICATION_JSON)
                               .retrieve()
                               .toEntityList(BranchRequestDTO.class);

               constructFinalOutput(element, Objects.requireNonNull(Objects.requireNonNull(repositoriesDetails.block()).getBody()));
           }
       }
   }

   private void constructFinalOutput(NameDTO repositoryDetails, List<BranchRequestDTO> branchDetails) {
       List<BranchDTO> branchDTOList = new ArrayList<>();
       branchDetails.forEach(ele -> {
           branchDTOList.add(new BranchDTO(ele.name(),ele.commit().sha()));
       });

       responseDTO.add(new RepoResponseDataDTO(
               repositoryDetails.repositoryName(),
               repositoryDetails.owner().login(),
               branchDTOList
               ));
   }
}


