package org.txor.beerapi.domain.exceptions;

public class BadManufacturerDataException extends RuntimeException {

    public BadManufacturerDataException(String name) {
        super(name + " does not match");
    }
}
