package org.txor.beerapi.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "manufacturer")
public class ManufacturerEntity {

    @Id
    private String name;
    private String nationality;

    protected ManufacturerEntity() {
    }

    public ManufacturerEntity(String name, String nationality) {
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
