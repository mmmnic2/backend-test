package com.backendtest.project.service;

import com.backendtest.project.dto.CreateBookRequest;
import com.backendtest.project.model.Book;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface BookService {
    Book createAndUpdateBook(CreateBookRequest createBookRequest) throws BadRequestException;
    Book findById(Long id);
    List<Book> findAll();
    void deleteById(Long id);
}
