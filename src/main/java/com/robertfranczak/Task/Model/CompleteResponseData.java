package com.robertfranczak.Task.Model;

import com.robertfranczak.Task.Model.DTO.RepoResponseDataDTO;

import java.util.List;
/**
 * CompleteResponseData is a DTO Object stores the array of repoResponseDatumDTOS.
 */
public record CompleteResponseData(List<RepoResponseDataDTO> repoResponseDatumDTOS) {
}
