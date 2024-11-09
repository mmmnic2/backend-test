package com.backendtest.project.validation;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * This interface defines common validation constraints for fields in the Book class.
 * It can be used in both DTO (Data Transfer Object) and Entity classes to ensure that data fields are valid
 * before performing operations like saving to the database or passing through the API.
 *
 * The validation constraints in this interface can be applied consistently across both the DTO and Entity
 * by having them implement this interface. Reusing validation like this ensures consistency across these classes.
 *
 * Note: In cases where the DTO or Entity classes have different validation requirements,
 * additional validation annotations can be added directly to those classes.
 *
 * Validation annotations used in this interface:
 * - @NotNull: Ensures the field is not null.
 * - @NotBlank: Ensures the field contains at least one non-whitespace character.
 * - @Positive: Ensures the field must be a number greater than 0.
 *
 * This approach allows validation to be defined once and reused in multiple places, reducing code duplication.
 */
public interface BookValidation {
    @NotBlank(message = "Title is required")
    @NotNull(message = "Title is required")
    String getTitle();

    @NotBlank(message = "Author is required")
    @NotNull(message = "Author is required")
    String getAuthor();

    /**
     * Retrieves the ISBN of the book.
     *
     * This method is annotated with two validation annotations to ensure the ISBN format is valid:
     *
     * 1. **@NotNull**: Ensures that the ISBN cannot be `null`. If the ISBN is not provided, a validation error will occur with the message:
     *    "ISBN is required".
     *
     * 2. **@Pattern**: Validates that the ISBN is in one of two accepted formats:
     *    - **ISBN-13 format**: Four groups of three digits separated by dashes (e.g., `xxx-xxx-xxx-xxx`).
     *    - **ISBN-10 format**: Three groups of digits separated by dashes, followed by a check digit (e.g., `xxx-xxx-xxx-x`).
     *    The regular expression used here is:
     *    - `\\d{3}-\\d{3}-\\d{3}-\\d{3}-\\d`: Matches ISBN-13 format.
     *    - `\\d{3}-\\d{3}-\\d{3}-\\d`: Matches a possible variant of ISBN-10 format.
     *
     * If the ISBN does not match one of the accepted formats, the validation error message:
     * "ISBN must be in the format xxx-xxx-xxx-xxx-x or xxx-xxx-xxx-x" will be triggered.
     *
     * @return the ISBN of the book in a valid format.
     */
    @NotNull(message = "ISBN is required")
    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{3}-\\d{3}-\\d|\\d{3}-\\d{3}-\\d{3}-\\d", message = "ISBN must be in the format xxx-xxx-xxx-xxx-x or xxx-xxx-xxx-x")
    String getIsbn();

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive number")
    BigDecimal getPrice();
}
