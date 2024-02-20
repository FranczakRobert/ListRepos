package com.robertfranczak.Task.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public class ExceptionConverter {
    private HttpStatus status;
    private String message;

    public Map<String,String> errorResponseJSON() {
        Map<String,String> map = new HashMap<>();
        map.put("message",message);
        map.put("status",String.valueOf(status.value()));
        return map;
    }

}
