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

    public void createNotFoundMsg() {
            String errorMessage = message.substring(message.indexOf("message\":\"") + 10);
            errorMessage = errorMessage.substring(0, errorMessage.indexOf("\""));
            message = errorMessage;
    }

    public void createNotAcceptableHeader() {
        message = "NotAcceptableHeader";
    }

    public Map<String,String> errorResponseJSON() {
        Map<String,String> map = new HashMap<>();
        map.put("message",message);
        map.put("status",String.valueOf(status.value()));
        return map;
    }

}
