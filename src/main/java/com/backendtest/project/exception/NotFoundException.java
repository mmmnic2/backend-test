package com.backendtest.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception thrown when a resource is not found.
 * This exception results in a {@link HttpStatus#NOT_FOUND} HTTP response status.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
    public NotFoundException(String e){
        super(e);
    }
}
