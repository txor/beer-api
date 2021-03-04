package org.txor.beerapi.domain.exceptions;

public class BadResourceDataException extends RuntimeException {

    public BadResourceDataException(String name) {
        super(name + " does not match");
    }
}
