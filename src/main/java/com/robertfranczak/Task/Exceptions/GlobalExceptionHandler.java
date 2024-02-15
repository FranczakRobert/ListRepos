package com.robertfranczak.Task.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Map<String,String> handleNotFoundException(NotFoundException e) {
        ExceptionConverter exceptionConverter = new ExceptionConverter(HttpStatus.NOT_FOUND,e.getMessage());
        exceptionConverter.errorForNotFoundMessage();
        return exceptionConverter.errorResponseJSON();
    }
}