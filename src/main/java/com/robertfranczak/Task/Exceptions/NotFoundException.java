package com.robertfranczak.Task.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * NotFoundException is an exception class representing a resource not found error.
 * It extends the RuntimeException class, and Invokes it constructor with message constructor parameter.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
