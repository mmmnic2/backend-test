package com.backendtest.project.service;

import com.backendtest.project.dto.BookUpsertRequest;
import com.backendtest.project.exception.AlreadyExistException;
import com.backendtest.project.exception.InvalidDateException;
import com.backendtest.project.exception.NotFoundException;
import com.backendtest.project.model.Book;
import com.backendtest.project.repository.BookRepository;
import com.backendtest.project.service.Impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private BookUpsertRequest bookUpsertRequest;
    private Book existingBook;

    @BeforeEach
    void setUp() {
        // Init mock annotations
        MockitoAnnotations.openMocks(this);
        // Create sample BookUpsertRequest
        bookUpsertRequest = new BookUpsertRequest();
        bookUpsertRequest.setBookId(1L);
        bookUpsertRequest.setTitle("Test Book");
        bookUpsertRequest.setAuthor("Author Test");
        bookUpsertRequest.setIsbn("123-456-789-1");
        bookUpsertRequest.setPrice(BigDecimal.valueOf(25.5));
        bookUpsertRequest.setPublishedDate("12/12/2022");

        // Create sample book in database
        existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("Test Book");
        existingBook.setAuthor("Author Test");
        existingBook.setIsbn("123-456-789-1");
        existingBook.setPrice(BigDecimal.valueOf(25.5));
        existingBook.setPublishedDate(new java.util.Date());
    }

    @Test
    @DisplayName("Create and update book when book ID exists, should update book")
    void testCreateAndUpdateBook_whenBookIdExists_shouldUpdateBook() throws ParseException {
        // Arrange
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(existingBook));
        when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(existingBook);

        // Act
        Book updatedBook = bookService.createAndUpdateBook(bookUpsertRequest);

        // Assert
        assertNotNull(updatedBook);
        assertEquals("Test Book", updatedBook.getTitle());
        assertEquals("Author Test", updatedBook.getAuthor());
        assertEquals("123-456-789-1", updatedBook.getIsbn());
    }

    @Test
    @DisplayName("Throw AlreadyExistException when book with same ISBN exists")
    void testCreateAndUpdateBook_whenBookWithSameIsbnExists_shouldThrowAlreadyExistException() {
        // Arrange
        Book anotherBook = new Book();
        anotherBook.setId(2L);
        anotherBook.setIsbn("123-456-789-1");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));

        when(bookRepository.findByIsbn("123-456-789-1")).thenReturn(Optional.of(anotherBook));

        // Act and Assert
        AlreadyExistException exception = assertThrows(AlreadyExistException.class, () -> {
            bookService.createAndUpdateBook(bookUpsertRequest);
        });
        assertEquals("Book with ISBN has already existed.", exception.getMessage());
    }

    @Test
    @DisplayName("Throw InvalidDateException when published date is in the future")
    void testCreateAndUpdateBook_whenPublishedDateIsInTheFuture_shouldThrowInvalidDateException() throws ParseException {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        bookUpsertRequest.setPublishedDate("12/12/2025");

        // Act and Assert
        InvalidDateException exception = assertThrows(InvalidDateException.class, () -> {
            bookService.createAndUpdateBook(bookUpsertRequest);
        });
        assertEquals("Published date must be in the past or present", exception.getMessage());
    }

    @Test
    @DisplayName("Return book when found by ID")
    void testFindById_whenBookExists_shouldReturnBook() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));

        // Act
        Book foundBook = bookService.findById(1L);

        // Assert
        assertNotNull(foundBook);
        assertEquals("Test Book", foundBook.getTitle());
    }

    @Test
    @DisplayName("Throw NotFoundException when book not found by ID")
    void testFindById_whenBookNotFound_shouldThrowNotFoundException() {
        // Arrange
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act and Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            bookService.findById(1L);
        });
        assertEquals("Book not found.", exception.getMessage());
    }

    @Test
    @DisplayName("Delete book when book exists")
    void testDeleteById_whenBookExists_shouldDeleteBook() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        doNothing().when(bookRepository).delete(existingBook);

        // Act
        bookService.deleteById(1L);

        // Assert
        verify(bookRepository, times(1)).delete(existingBook);
    }

    @Test
    @DisplayName("Throw NotFoundException when book not found for deletion")
    void testDeleteById_whenBookNotFound_shouldThrowNotFoundException() {
        // Arrange
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act and Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            bookService.deleteById(1L);
        });
        assertEquals("Book not found.", exception.getMessage());
    }
}
