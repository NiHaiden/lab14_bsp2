package dev.nhaiden.lab14_bsp2.rest;

import dev.nhaiden.lab14_bsp2.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CentralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllOtherExceptions(Exception ex, WebRequest rq) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IdIsNullException.class)
    public ResponseEntity<String> handleIdIsNullException(IdIsNullException ie, WebRequest rq) {
        return new ResponseEntity<>(ie.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IdNotNullException.class)
    public ResponseEntity<String> handleIdNotNullException(IdNotNullException notNullException, WebRequest rq) {
        return new ResponseEntity<>(notNullException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<String> handlePostNotFoundEx(PostNotFoundException pnfe, WebRequest rq) {
        return new ResponseEntity<>(pnfe.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsEx(UserAlreadyExistsException ex, WebRequest rq) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundEx(UserNotFoundException ex, WebRequest rq) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid (MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>("Validation failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
