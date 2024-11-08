package com.backendtest.project.exception;

import com.backendtest.project.dto.ResponseDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
    @ExceptionHandler(BadRequestException.class)
    public ResponseDTO handleBadRequestException(BadRequestException ex) {
        return new ResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
    }
}
