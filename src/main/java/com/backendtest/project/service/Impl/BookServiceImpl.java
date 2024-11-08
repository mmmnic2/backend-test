package com.backendtest.project.service.Impl;

import com.backendtest.project.dto.CreateBookRequest;
import com.backendtest.project.exception.AlreadyExistException;
import com.backendtest.project.exception.NotFoundException;
import com.backendtest.project.model.Book;
import com.backendtest.project.repository.BookRepository;
import com.backendtest.project.service.BookService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    @Override
    public Book createAndUpdateBook(CreateBookRequest createBookRequest) throws BadRequestException {
        Book savedBook = new Book();
        if(createBookRequest.getBookId() != null){
            savedBook = bookRepository.findById(createBookRequest.getBookId()).orElseThrow(() -> new NotFoundException("Book not found."));
        }
        Optional<Book> foundBook = bookRepository.findByIsbn(createBookRequest.getIsbn());
        if (foundBook.isPresent()) throw new AlreadyExistException("Book with ISBN already exists.");
        if(createBookRequest.getPrice() < 0) throw new BadRequestException("Price cannot be less than 0.");
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
    public void deleteById(Long id) {
        Book foundBook = bookRepository.findById(id).orElseThrow(()-> new NotFoundException("Book not found."));
        bookRepository.delete(foundBook);
    }


}
