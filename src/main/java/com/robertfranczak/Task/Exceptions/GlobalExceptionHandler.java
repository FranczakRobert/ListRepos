package com.robertfranczak.Task.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public Map<String,String> handleNotFoundException(NotFoundException exception) {
        ExceptionConverter exceptionConverter = new ExceptionConverter(HttpStatus.NOT_FOUND,exception.getMessage());
        return exceptionConverter.errorResponseJSON();
    }
}