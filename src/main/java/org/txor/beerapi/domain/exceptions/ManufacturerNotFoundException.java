package org.txor.beerapi.domain.exceptions;

public class ManufacturerNotFoundException extends RuntimeException {

    public ManufacturerNotFoundException(String name) {
        super(name + " not found");
    }
}
