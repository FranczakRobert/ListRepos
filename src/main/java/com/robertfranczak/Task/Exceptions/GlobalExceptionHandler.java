package com.robertfranczak.Task.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * GlobalExceptionHandler is a centralized exception handler for handling specific exceptions.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the NotFoundException exception.
     * It passes the exception parameter to the ExceptionConverter class where it is handled.
     * This method returns a JSON representation of the error response with HTTP status 404 (Not Found).
     *
     * @param exception The NotFoundException that occurred.
     * @return A Map representing the JSON error response with "message" and "status" keys.
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Map<String,String> handleNotFoundException(NotFoundException exception) {
        ExceptionConverter exceptionConverter = new ExceptionConverter(HttpStatus.NOT_FOUND,exception.getMessage());
        exceptionConverter.errorForNotFoundMessage();
        return exceptionConverter.errorResponseJSON();
    }
}