package com.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse exceptionResponse(NotFoundException e) {
        return new ExceptionResponse(e.getMessage(),
                HttpStatus.NOT_FOUND, e.getClass().getSimpleName());

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse badRequest(BadRequestException e){
        return new ExceptionResponse(e.getMessage(),HttpStatus.BAD_REQUEST, e.getClass().getSimpleName());
    }
}
