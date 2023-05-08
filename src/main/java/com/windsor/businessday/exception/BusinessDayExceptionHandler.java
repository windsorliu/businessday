package com.windsor.businessday.exception;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.text.ParseException;

@ControllerAdvice
public class BusinessDayExceptionHandler {

    private final static Logger log = LoggerFactory.getLogger(BusinessDayExceptionHandler.class);

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handler(FileNotFoundException e) {
        log.warn("The file cannot be found, please confirm that the year is entered correctly.");
        log.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(JSONException.class)
    public ResponseEntity<?> handler(JSONException e) {
        return internalServerError(e);
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<?> handler(ParseException e) {
        return internalServerError(e);
    }

    private ResponseEntity<?> internalServerError(Exception e) {
        log.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
