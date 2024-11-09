package com.backendtest.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception thrown when a resource is already existed in repository.
 * This exception results in a {@link HttpStatus#NOT_ACCEPTABLE} HTTP response status.
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException(String message) {
        super(message);
    }
}
