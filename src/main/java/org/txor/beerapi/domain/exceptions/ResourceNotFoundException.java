package org.txor.beerapi.domain.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String name) {
        super(name + " not found");
    }
}
