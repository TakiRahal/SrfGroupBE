package com.takirahal.srfgroup.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(ResouorceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handeleResouorceNotFoundException(ResouorceNotFoundException ex, WebRequest request) {
        log.error("An exception have been occurred please see logging error", ex.getMessage());
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }




    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorMessage> handeleInvalidPasswordException(InvalidPasswordException ex, WebRequest request) {
        log.error("An exception have been occurred please see logging error", ex.getMessage());
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<ErrorMessage> handeleEmailAlreadyUsedException(EmailAlreadyUsedException ex, WebRequest request) {
        log.error("An exception have been occurred please see logging error", ex.getMessage());
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountResourceException.class)
    public ResponseEntity<ErrorMessage> handeleAccountResourceException(AccountResourceException ex, WebRequest request) {
        log.error("An exception have been occurred please see logging error", ex.getMessage());
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotActivatedException.class)
    public ResponseEntity<ErrorMessage> handeleUserNotActivatedException(UserNotActivatedException ex, WebRequest request) {
        log.error("An exception have been occurred please see logging error", ex.getMessage());
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
