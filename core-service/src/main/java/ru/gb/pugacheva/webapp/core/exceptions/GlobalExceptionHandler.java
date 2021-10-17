package ru.gb.pugacheva.webapp.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.gb.pugacheva.webapp.api.exceptions.MarketError;
import ru.gb.pugacheva.webapp.api.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> catchResourceNotFoundException (ResourceNotFoundException e){
        return new ResponseEntity<>(new MarketError(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity <?> catchDataValidationException (DataValidationException e){
        return new ResponseEntity<>(new MarketError(e.getMessages()), HttpStatus.BAD_REQUEST);
    }
}
