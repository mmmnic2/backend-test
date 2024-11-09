package com.backendtest.project.service.Impl;

import com.backendtest.project.dto.BookUpsertRequest;
import com.backendtest.project.exception.AlreadyExistException;
import com.backendtest.project.exception.InvalidDateException;
import com.backendtest.project.exception.NotFoundException;
import com.backendtest.project.model.Book;
import com.backendtest.project.repository.BookRepository;
import com.backendtest.project.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    /**
     * Converts a string in "MM/dd/yyyy" format to a {@link Date} object.
     *
     * @param dateStr the date string in "MM/dd/yyyy" format.
     * @return the corresponding {@link Date} object.
     * @throws ParseException if the date string is invalid or cannot be parsed.
     */
    private Date convertStringToDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.parse(dateStr);
    }

    /**
     * Checks if the given date is in the past or present.
     *
     * @param date the date to check.
     * @return true if the date is in the past or today, false if it is in the future.
     */
    private boolean isDateInThePastOrPresent(Date date) {
            Date currentDate = new Date();
            return !date.after(currentDate);
    }

    /**
     * Creates or updates a book based on the provided {@link BookUpsertRequest}.
     * If a book with the provided ISBN already exists, an {@link AlreadyExistException} is thrown.
     * If a book ID is provided, it updates the existing book, otherwise, it creates a new one.
     * The published date must be in the past or present, otherwise an {@link InvalidDateException} is thrown.
     *
     * @param bookUpsertRequest the request containing book details.
     * @return the saved or updated {@link Book}.
     * @throws ParseException if the published date cannot be parsed.
     * @throws NotFoundException if the book ID is provided but the book is not found.
     * @throws AlreadyExistException if a book with the same ISBN already exists.
     * @throws InvalidDateException if the published date is in the future.
     */
    @Override
    public Book createAndUpdateBook(BookUpsertRequest bookUpsertRequest) throws ParseException {
        Book savedBook = new Book();
        // Find and check bookId if it exists in RequestBody
        if(bookUpsertRequest.getBookId() != null){
            savedBook = bookRepository.findById(bookUpsertRequest.getBookId()).orElseThrow(() -> new NotFoundException("Book not found."));
        }
        // Find and check existing ISBN if true throw error
        Optional<Book> foundBook = bookRepository.findByIsbn(bookUpsertRequest.getIsbn());
        if (foundBook.isPresent()) throw new AlreadyExistException("Book with ISBN has already existed.");
        savedBook.setAuthor(bookUpsertRequest.getAuthor());
        savedBook.setIsbn(bookUpsertRequest.getIsbn());
        savedBook.setPrice(bookUpsertRequest.getPrice());
        savedBook.setTitle(bookUpsertRequest.getTitle());
        // Check publish date in RequestBody before save it to database
        if(!isDateInThePastOrPresent(convertStringToDate(bookUpsertRequest.getPublishedDate()))){
            throw new InvalidDateException("Published date must be in the past or present");
        }
        savedBook.setPublishedDate(convertStringToDate(bookUpsertRequest.getPublishedDate()));
        return bookRepository.save(savedBook);
    }

    /**
     * Finds a book by its ID.
     * If the book is not found, a {@link NotFoundException} is thrown.
     *
     * @param id the ID of the book to find.
     * @return the {@link Book} object with the specified ID.
     * @throws NotFoundException if the book with the given ID does not exist.
     */
    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(()-> new NotFoundException("Book not found."));
    }

    /**
     * Retrieves all books from the repository.
     *
     * @return a list of all {@link Book} objects.
     */
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * Retrieves a paginated list of books along with the total count.
     *
     * @param pageable the pagination information (page, size, sorting).
     * @return a map containing the total count of books from the repository and the list of books for the current page.
     *         The map contains:
     *         - "totalCount" (total number of books)
     *         - "data" (list of books for the current page)
     */
    @Override
    public Map<String, Object> findAllWithPagination(Pageable pageable) {
        Page<Book> data = bookRepository.findAll(pageable);
        Map<String, Object> res = new HashMap<>();
        res.put("totalCount", data.getTotalElements());
        res.put("data", data.getContent());
        return res;
    }

    /**
     * Deletes a book by its ID.
     * If the book is not found, a {@link NotFoundException} is thrown.
     *
     * @param id the ID of the book to delete.
     * @throws NotFoundException if the book with the given ID does not exist.
     */
    @Override
    public void deleteById(Long id) {
        Book foundBook = bookRepository.findById(id).orElseThrow(()-> new NotFoundException("Book not found."));
        bookRepository.delete(foundBook);
    }


}
