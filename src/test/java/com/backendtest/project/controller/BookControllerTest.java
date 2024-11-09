package com.backendtest.project.controller;

import com.backendtest.project.dto.BookUpsertRequest;
import com.backendtest.project.exception.GlobalExceptionHandler;
import com.backendtest.project.exception.NotFoundException;
import com.backendtest.project.model.Book;
import com.backendtest.project.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.*;

@WebMvcTest(controllers = BookController.class)
@Import(GlobalExceptionHandler.class)
public class BookControllerTest {
    @Autowired
    BookController bookController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
    @Autowired
    private ObjectMapper objectMapper;

    private Book book;
    private BookUpsertRequest bookUpsertRequest;

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
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Author Test");
        book.setIsbn("123-456-789-1");
        book.setPrice(BigDecimal.valueOf(25.5));
        book.setPublishedDate(new java.util.Date());
    }

    @Test
    @DisplayName("Should return all books with a success response")
    void testGetAllBooks_Success() throws Exception {
        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.result").isArray())
                .andDo(print());
    }

    @Test
    @DisplayName("Should return a book match request bookId with a success response")
    void testGetBookById_Success() throws Exception {
        Mockito.when(bookService.findById(1L)).thenReturn(book);
        Book resultMock = bookService.findById(1L);
        assertEquals("Test Book", resultMock.getTitle());
        ResultActions result = mockMvc.perform(get("/api/v1/book/1"));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.result.title").value("Test Book"))
                .andDo(print());
    }

    @Test
    @DisplayName("Should return 404 Not Found when book Id does not exist")
    void testGetBookById_NotFound() throws Exception {
        Long invalidBookId = 99L;
        Mockito.when(bookService.findById(invalidBookId)).thenThrow(new NotFoundException("Book not found"));

        ResultActions result = mockMvc.perform(get("/api/v1/book/" + invalidBookId));

        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("Book not found"))
                .andDo(print());
    }

    @Test
    @DisplayName("Should return 404 Not Found when deleting book Id does not exist")
    void testDeleteBookById_NotFound() throws Exception {
        Long invalidBookId = 99L;
        Mockito.doThrow(new NotFoundException("Book not found")).when(bookService).deleteById(invalidBookId);

        mockMvc.perform(delete("/api/v1/books/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("Book not found"))
                .andDo(print());
    }

    @Test
    @DisplayName("Should return success message when deleting book by book Id")
    void testDeleteBookById_Success() throws Exception {
        Mockito.doNothing().when(bookService).deleteById(1L);

        // Thực hiện yêu cầu DELETE
        mockMvc.perform(delete("/api/v1/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    @Test
    @DisplayName("Should return 200 OK with empty content when no books are available for pagination")
    void testGetAllBooksWithPagination_NoContent() throws Exception {

        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("content", Collections.emptyList());
        mockResponse.put("totalPages", 0);
        mockResponse.put("totalElements", 0);

        Pageable pageable = PageRequest.of(0, 5);
        Mockito.when(bookService.findAllWithPagination(pageable)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/books-pagination")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.result.content").isEmpty())
                .andExpect(jsonPath("$.result.totalPages").value(0))
                .andExpect(jsonPath("$.result.totalElements").value(0))
                .andDo(print());
    }

    @Test
    @DisplayName("Should return 200 OK with content when with books are available for pagination")
    void testGetAllBooksWithPagination_Success() throws Exception {
        List<Book> mockBooks = Arrays.asList(
                new Book(1L, "Book 1", "Author 1", "101-100-102-102-1", BigDecimal.valueOf(10.99)),
                new Book(2L, "Book 2", "Author 2", "101-100-102-102-2", BigDecimal.valueOf(12.99)),
                new Book(3L, "Book 3", "Author 3", "101-100-102-102-3", BigDecimal.valueOf(8.99))
        );

        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("content", mockBooks);
        mockResponse.put("totalPages", 1);
        mockResponse.put("totalElements", 3);

        Pageable pageable = PageRequest.of(0, 5);
        Mockito.when(bookService.findAllWithPagination(pageable)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/books-pagination")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())  // Kiểm tra mã trạng thái HTTP
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.result.content[0].title").value("Book 1"))
                .andExpect(jsonPath("$.result.content[1].title").value("Book 2"))
                .andExpect(jsonPath("$.result.content[2].title").value("Book 3"))
                .andExpect(jsonPath("$.result.totalPages").value(1))
                .andExpect(jsonPath("$.result.totalElements").value(3))
                .andDo(print());
    }

    @Test
    @DisplayName("Should return 200 OK when create and update book success")
    void testCreateAndUpdateBook_Success() throws Exception {
        BookUpsertRequest bookUpsertRequest = new BookUpsertRequest();
        bookUpsertRequest.setBookId(1L);
        bookUpsertRequest.setTitle("Book1");
        bookUpsertRequest.setAuthor("AuthorA");
        bookUpsertRequest.setPublishedDate("10/01/2023");
        bookUpsertRequest.setIsbn("123-456-789-1");
        bookUpsertRequest.setPrice(BigDecimal.valueOf(29.99));

        String jsonRequest = objectMapper.writeValueAsString(bookUpsertRequest);

        Book mockBook = new Book(1L, "Title", "Author", "101-100-102-102-1", BigDecimal.valueOf(10.99));
        Mockito.when(bookService.createAndUpdateBook(Mockito.any(BookUpsertRequest.class))).thenReturn(mockBook);

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    @Test
    @DisplayName("Should return 400 BAD REQUEST with error message when one of request body is invalid")
    void testCreateAndUpdateBook_BadRequest() throws Exception {
        BookUpsertRequest bookUpsertRequest = new BookUpsertRequest();
        bookUpsertRequest.setBookId(1L);
        bookUpsertRequest.setTitle("Book1");
        bookUpsertRequest.setAuthor("");
        bookUpsertRequest.setPublishedDate("10/01/2023");
        bookUpsertRequest.setIsbn("123-456-789-1");
        bookUpsertRequest.setPrice(BigDecimal.valueOf(29.99));

        String jsonRequest = objectMapper.writeValueAsString(bookUpsertRequest);

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("Author is required"))
                .andDo(print());
    }



}
