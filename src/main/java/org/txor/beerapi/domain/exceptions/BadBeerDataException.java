package org.txor.beerapi.domain.exceptions;

public class BadBeerDataException extends RuntimeException {

    public BadBeerDataException(String name) {
        super(name + " does not match");
    }
}
