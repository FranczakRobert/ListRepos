package com.robertfranczak.Task.Exceptions;

import org.springframework.http.HttpStatus;

record ApiException (
        HttpStatus status,
        String message
){}
