package com.takirahal.srfgroup.exceptions.advice;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.exceptions.ErrorMessage;
import com.takirahal.srfgroup.exceptions.InvalidPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class OfferControllerExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(OfferControllerExceptionHandler.class);

    @ExceptionHandler(BadRequestAlertException.class)
    public ResponseEntity<ErrorMessage> handeleInvalidPasswordException(BadRequestAlertException ex, WebRequest request) {
        log.error("An exception have been occurred please see logging error", ex.getMessage());
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
