package org.txor.beerapi.domain.exceptions;

public class BeerNotFoundException extends RuntimeException {

    public BeerNotFoundException(String name) {
        super(name + " not found");
    }
}
