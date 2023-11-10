package com.example.boardproject.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "ENTITY NOT FOUND")
public class DataNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1873527683909274536L;
    public DataNotFoundException(String message) {
        super (message);
    }
}
