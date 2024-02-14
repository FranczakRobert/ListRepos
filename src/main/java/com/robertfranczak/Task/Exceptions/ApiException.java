package com.robertfranczak.Task.Exceptions;

public record ApiException (
        int status,
        String message
){}
