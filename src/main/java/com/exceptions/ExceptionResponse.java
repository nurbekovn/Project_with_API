package com.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {
    private String message;
    private HttpStatus httpStatus;
    private String exceptionClassName;

    public ExceptionResponse(String message, HttpStatus notFound, String simpleName, String message1, HttpStatus badRequest, String simpleName1) {
        this.message = message;
        this.exceptionClassName = getExceptionClassName();
    }

}
