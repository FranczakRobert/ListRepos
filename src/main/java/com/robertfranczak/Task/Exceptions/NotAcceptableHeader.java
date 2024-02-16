package com.robertfranczak.Task.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class NotAcceptableHeader extends RuntimeException{
    public NotAcceptableHeader(String message) {
        super(message);
    }
}
