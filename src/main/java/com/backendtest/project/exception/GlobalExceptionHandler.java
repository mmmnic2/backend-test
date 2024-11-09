package com.backendtest.project.exception;

import com.backendtest.project.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.ParseException;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseDTO handleNotFoundException(NotFoundException ex) {
        return new ResponseDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
    }
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseDTO handleAlreadyExistException(AlreadyExistException ex) {
        return new ResponseDTO(HttpStatus.CONFLICT.value(), ex.getMessage(), null);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Invalid JSON format " + e.getLocalizedMessage(), null);
    }
    @ExceptionHandler(ParseException.class)
    public ResponseDTO handleParseException(ParseException ex) {
        return new ResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
    }
    @ExceptionHandler(InvalidDateException.class)
    public ResponseDTO handleInvalidDateException(InvalidDateException ex) {
        return new ResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
    }
}
