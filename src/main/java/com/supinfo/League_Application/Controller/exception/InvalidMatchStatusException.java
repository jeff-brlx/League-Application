package com.supinfo.League_Application.Controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidMatchStatusException extends RuntimeException {
    public InvalidMatchStatusException(String message) {
        super(message);
    }
}

