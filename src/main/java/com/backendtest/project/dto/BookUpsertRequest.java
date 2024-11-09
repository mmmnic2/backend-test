package com.backendtest.project.dto;

import com.backendtest.project.validation.BookValidation;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


/**
 * Data Transfer Object for creating or updating a Book.
 * This DTO includes validation annotations to ensure that only
 * valid data is submitted when making a request.
 */
@NoArgsConstructor
@Getter
@Setter
public class BookUpsertRequest implements BookValidation {

    private Long bookId;

    private String title;

    private String author;

    /**
     * Validation for publish date request
     * Because of Pattern can not be use for Date type, so we will use String for Pattern validate
     * If data is valid, it will be converted to Date at convertStringToDate in {@link com.backendtest.project.service.Impl.BookServiceImpl}.
     */
    @NotNull
    @Pattern(regexp = "^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])-\\d{4}$", message = "Published date must be in format MM-dd-yyyy")
    @JsonFormat(pattern = "MM-dd-yyyy")
    private String publishedDate;

    private String isbn;

    private BigDecimal price;

}
