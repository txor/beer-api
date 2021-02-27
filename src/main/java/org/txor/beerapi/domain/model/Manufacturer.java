package org.txor.beerapi.domain.model;

public class Manufacturer {

    private final String name;
    private final String nationality;

    public Manufacturer(String name, String nationality) {
        this.name = name;
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }
}
