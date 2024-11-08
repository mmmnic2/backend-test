package com.backendtest.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@NoArgsConstructor
@Getter
@Setter
public class CreateBookRequest {
    private Long bookId;
    private String title;
    private String author;
    private Date publishedDate;
    private String isbn;
    private Double price;
}
