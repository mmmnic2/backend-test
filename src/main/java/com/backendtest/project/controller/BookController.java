package com.backendtest.project.controller;

import com.backendtest.project.dto.CreateBookRequest;
import com.backendtest.project.dto.ResponseDTO;
import com.backendtest.project.model.Book;
import com.backendtest.project.service.BookService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController("/api/v1/")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/books")
    public ResponseDTO createAndUpdateBook(@RequestBody CreateBookRequest createBookRequest) throws BadRequestException {
        Book response = bookService.createAndUpdateBook(createBookRequest);
        if (response != null) {
            return new ResponseDTO(HttpStatus.OK.value(), "success", null);
        } else
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "internal error", null);

    }

    @GetMapping("/books")
    public ResponseDTO getAll() {
        return new ResponseDTO(HttpStatus.OK.value(), "success", bookService.findAll());
    }

    @GetMapping("/book/{id}")
    public ResponseDTO getById(@PathVariable Long id) {
        Book response = bookService.findById(id);
        if (response != null) {
            return new ResponseDTO(HttpStatus.OK.value(), "success", response);
        } else
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "internal error", null);

    }

    @DeleteMapping("/books/{id}")
    public ResponseDTO deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
        return new ResponseDTO(HttpStatus.OK.value(), "success", null);
    }
}
