package com.backendtest.project.service;

import com.backendtest.project.dto.CreateBookRequest;
import com.backendtest.project.model.Book;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BookService {
    Book createAndUpdateBook(CreateBookRequest createBookRequest);
    Book findById(Long id);
    List<Book> findAll();
    Map<String, Object> findAllWithPagination(Pageable pageable);
    void deleteById(Long id);
}
