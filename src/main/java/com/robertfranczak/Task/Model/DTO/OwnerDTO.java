package com.robertfranczak.Task.Model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * OwnerDTO is a DTO Object stores the value of login.
 * It is used to transfer branch data between different layers of the application.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OwnerDTO(
        String login
){}
