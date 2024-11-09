package com.backendtest.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class CreateBookRequest {

    private Long bookId;

    @NotBlank(message = "Title is required")
    @NotNull(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    @NotNull(message = "Author is required")
    private String author;

    @PastOrPresent(message = "Published date must be in the past or present")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date publishedDate;

    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{3}-\\d{3}-\\d|\\d{3}-\\d{3}-\\d{3}-\\d", message = "ISBN must be in the format xxx-xxx-xxx-xxx-x or xxx-xxx-xxx-x")
    private String isbn;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive number")
    private Double price;
}
