package org.txor.beerapi.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.txor.beerapi.domain.exceptions.BeerNotFoundException;

@ControllerAdvice
public class BeerControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(BeerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String beerNotFoundHandler(BeerNotFoundException ex) {
        return ex.getMessage();
    }
}
