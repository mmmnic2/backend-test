package com.backendtest.project.controller;

import com.backendtest.project.dto.CreateBookRequest;
import com.backendtest.project.dto.ResponseDTO;
import com.backendtest.project.model.Book;
import com.backendtest.project.service.BookService;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/books-pagination")
    public ResponseDTO getAll(@RequestParam(required = false, defaultValue = "0") int page,
                              @RequestParam(required = false, defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Map<String, Object> responses = bookService.findAllWithPagination(pageable);
        return new ResponseDTO(HttpStatus.OK.value(), "success", responses);
    }

    @PostMapping("/books")
    public ResponseDTO createAndUpdateBook(@RequestBody @Valid CreateBookRequest createBookRequest,
                                            BindingResult bindingResult)  {
        if (bindingResult.hasErrors()) return new ResponseDTO(HttpStatus.BAD_REQUEST.value(), bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
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
