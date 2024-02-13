package com.phs.rinha.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

import static org.springframework.http.ResponseEntity.notFound;

@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler({ServerWebInputException.class, WebExchangeBindException.class})
    ResponseEntity invalidRequest(Exception ex) {
        return ResponseEntity.unprocessableEntity().build();
    }

}