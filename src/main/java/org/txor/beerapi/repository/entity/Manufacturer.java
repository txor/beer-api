package org.txor.beerapi.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Manufacturer {

    @Id
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
