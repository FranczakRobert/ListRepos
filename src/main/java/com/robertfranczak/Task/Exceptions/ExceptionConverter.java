package com.robertfranczak.Task.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
/**
 * ExceptionConverter is a utility class responsible for converting exceptions
 * into appropriate HTTP status codes and error messages.
 * This class provides methods to handle exception messages and generate
 * JSON error responses.
 */
@Getter
@AllArgsConstructor
public class ExceptionConverter {
    /** The HTTP status code associated with the exception. */
    private HttpStatus status;

    /** The error message associated with the exception. */
    private String message;


    /**
     * Extracts the error message from the exception message and set message variable to specific errorMessage.
     */
    public void createNotFoundMsg() {
            String errorMessage = message.substring(message.indexOf("message\":\"") + 10);
            errorMessage = errorMessage.substring(0, errorMessage.indexOf("\""));
            message = errorMessage;
    }

    /**
     * Create NotAcceptableHeader error message
     */
    public void createNotAcceptableHeader() {
        message = "NotAcceptableHeader";
    }

    /**
     * Generates a JSON representation of the error response.
     * The JSON object contains the error message and the HTTP status code.
     * @return A Map representing the JSON error response with "message" and "status" keys.
     */
    public Map<String,String> errorResponseJSON() {
        Map<String,String> map = new HashMap<>();
        map.put("message",message);
        map.put("status",String.valueOf(status.value()));
        return map;
    }

}
