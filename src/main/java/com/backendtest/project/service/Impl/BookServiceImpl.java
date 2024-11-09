package com.backendtest.project.service.Impl;

import com.backendtest.project.dto.CreateBookRequest;
import com.backendtest.project.exception.AlreadyExistException;
import com.backendtest.project.exception.NotFoundException;
import com.backendtest.project.model.Book;
import com.backendtest.project.repository.BookRepository;
import com.backendtest.project.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    @Override
    public Book createAndUpdateBook(CreateBookRequest createBookRequest) {
        Book savedBook = new Book();
        if(createBookRequest.getBookId() != null){
            savedBook = bookRepository.findById(createBookRequest.getBookId()).orElseThrow(() -> new NotFoundException("Book not found."));
        }
        Optional<Book> foundBook = bookRepository.findByIsbn(createBookRequest.getIsbn());
        if (foundBook.isPresent()) throw new AlreadyExistException("Book with ISBN has already existed.");
        savedBook.setAuthor(createBookRequest.getAuthor());
        savedBook.setIsbn(createBookRequest.getIsbn());
        savedBook.setPrice(createBookRequest.getPrice());
        savedBook.setTitle(createBookRequest.getTitle());
        savedBook.setPublishedDate(createBookRequest.getPublishedDate());
        return bookRepository.save(savedBook);
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(()-> new NotFoundException("Book not found."));
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Map<String, Object> findAllWithPagination(Pageable pageable) {
        Page<Book> data = bookRepository.findAll(pageable);
        Map<String, Object> res = new HashMap<>();
        res.put("totalCount", data.getTotalElements());
        res.put("data", data.getContent());
        return res;
    }

    @Override
    public void deleteById(Long id) {
        Book foundBook = bookRepository.findById(id).orElseThrow(()-> new NotFoundException("Book not found."));
        bookRepository.delete(foundBook);
    }


}
