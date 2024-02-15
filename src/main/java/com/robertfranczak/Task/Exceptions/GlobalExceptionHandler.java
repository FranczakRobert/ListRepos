package com.robertfranczak.Task.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
     *
     * @param exception The NotFoundException that occurred.
     * @return A Map representing the JSON error response with "message" and "status" keys.
     */
    @ExceptionHandler(NotFoundException.class)
    public Map<String,String> handleNotFoundException(NotFoundException exception) {
        ExceptionConverter exceptionConverter = new ExceptionConverter(HttpStatus.NOT_FOUND,exception.getMessage());
        exceptionConverter.createNotFoundMsg();
        return exceptionConverter.errorResponseJSON();
    }

    /**
     * Handles the NotAcceptableHeader exception.
     * It passes the exception parameter to the ExceptionConverter class where it is handled.
     *
     * @param exception The NotAcceptableHeader that occurred.
     * @return A Map representing the JSON error response with "message" and "status" keys.
     */
    @ExceptionHandler(NotAcceptableHeader.class)
    public Map<String,String> handleNotAcceptableHeader(NotAcceptableHeader exception) {
        ExceptionConverter exceptionConverter = new ExceptionConverter(HttpStatus.NOT_ACCEPTABLE,exception.getMessage());
        exceptionConverter.createNotAcceptableHeader();
        return exceptionConverter.errorResponseJSON();
    }
}