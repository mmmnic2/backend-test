package com.backendtest.project.service;

import com.backendtest.project.dto.BookUpsertRequest;
import com.backendtest.project.model.Book;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface BookService {
    Book createAndUpdateBook(BookUpsertRequest createBookRequest) throws ParseException;
    Book findById(Long id);
    List<Book> findAll();
    Map<String, Object> findAllWithPagination(Pageable pageable);
    void deleteById(Long id);
}
