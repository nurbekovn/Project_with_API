package com.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)

    public ExceptionResponse exceptionResponse(NotFoundException e,BadRequestException b) {
        return new ExceptionResponse(e.getMessage(),
                HttpStatus.NOT_FOUND, e.getClass().getSimpleName(),
                b.getMessage(),
                HttpStatus.BAD_REQUEST, b.getClass().getSimpleName());

    }
}
