package com.backendtest.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception thrown when a date is invalid (e.g. wrong format, in the future).
 * This exception results in a {@link HttpStatus#BAD_REQUEST} HTTP response status.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDateException extends RuntimeException{
    public InvalidDateException(String e){
        super(e);
    }
}
