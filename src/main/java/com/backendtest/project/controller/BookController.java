package com.backendtest.project.controller;

import com.backendtest.project.dto.BookUpsertRequest;
import com.backendtest.project.dto.ResponseDTO;
import com.backendtest.project.model.Book;
import com.backendtest.project.service.BookService;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

/**
 * REST controller for handling book-related operations.
 * This controller includes endpoints for creating, retrieving, updating, and deleting books.
 */
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    /**
     * Retrieves a paginated list of books.
     *
     * - The `page` parameter specifies the page number (0-based index).
     * - The `size` parameter specifies the number of items per page.
     *
     * If `page` or `size` parameters are not provided, they default to 0 and 5, respectively.
     * The method uses the `PageRequest.of(page, size)` to create a Pageable instance for pagination.
     */
    @GetMapping("/books-pagination")
    public ResponseEntity<ResponseDTO> getAll(@RequestParam(required = false, defaultValue = "0") int page,
                                              @RequestParam(required = false, defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Map<String, Object> responses = bookService.findAllWithPagination(pageable);
        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "success", responses));
    }

    @PostMapping("/books")
    public ResponseEntity<ResponseDTO> createAndUpdateBook(@RequestBody @Valid BookUpsertRequest bookUpsertRequest,
                                            BindingResult bindingResult) throws ParseException {
        if (bindingResult.hasErrors()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(), bindingResult.getAllErrors().get(0).getDefaultMessage(), null));
        Book response = bookService.createAndUpdateBook(bookUpsertRequest);
        if (response != null) {
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "success", null));
        } else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "internal error", null));

    }

    @GetMapping("/books")
    public ResponseEntity<ResponseDTO> getAll() {
        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "success", bookService.findAll()));
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable Long id) {
        Book response = bookService.findById(id);
        if (response != null) {
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "success", response));
        } else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "internal error", null));

    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "success", null));
    }
}
