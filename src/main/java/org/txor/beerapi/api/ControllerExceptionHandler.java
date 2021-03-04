package org.txor.beerapi.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.txor.beerapi.domain.exceptions.BadResourceDataException;
import org.txor.beerapi.domain.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String beerNotFoundHandler(ResourceNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(BadResourceDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String badBeerDataHandler(BadResourceDataException ex) {
        return ex.getMessage();
    }
}
