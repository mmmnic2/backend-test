package com.backendtest.project.exception;

import com.backendtest.project.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.ParseException;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDTO> handleNotFoundException(NotFoundException ex) {
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ResponseDTO> handleAlreadyExistException(AlreadyExistException ex) {
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.CONFLICT.value(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDTO);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Invalid JSON format " + ex.getLocalizedMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }
    @ExceptionHandler(ParseException.class)
    public ResponseEntity<ResponseDTO> handleParseException(ParseException ex) {
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }
    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<ResponseDTO> handleInvalidDateException(InvalidDateException ex) {
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }
}
