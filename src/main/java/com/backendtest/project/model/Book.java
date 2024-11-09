package com.backendtest.project.model;

import com.backendtest.project.validation.BookValidation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Represents a Book entity with attributes such as ISBN, title, author, and publication year.
 * Each attribute is annotated with validation constraints to ensure the data's integrity
 * before it is stored in the database.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "book")
public class Book implements BookValidation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    @NotNull
    @PastOrPresent(message = "Published date must be in the past or present")
    private Date publishedDate;
    private String isbn;
    @Column(scale = 2)
    private BigDecimal price;

    public Book(long id, String title, String author, String isbn, BigDecimal bigDecimal) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = bigDecimal;
    }
}
