package com.xm.recommendation.crypto.controller;

import com.xm.recommendation.crypto.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class CryptoControllerAdvice {

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorMessage> handleParseException() {
        ErrorMessage msg = new ErrorMessage("Invalid date format. Please use the format yyyy-MM-dd.",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentTypeMismatchException() {
        ErrorMessage msg = new ErrorMessage("This crypto is not supported yet",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException() {
        ErrorMessage msg = new ErrorMessage("Invalid value for parameter",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorMessage> handleNullPointerException() {
        ErrorMessage msg = new ErrorMessage("Missing parameter value ",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }
}