package com.backendtest.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A Data Transfer Object (DTO) for standardizing API responses.
 * This class is used to structure the response data, including a status flag, a message, and the actual result.
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
    private int statusCode;
    private String message;
    private Object result;
}
