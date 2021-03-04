package org.txor.beerapi.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "beer")
public class BeerEntity {

    @Id
    private String name;
    private Float graduation;
    private String type;
    private String description;
    @ManyToOne
    @JoinColumn(name = "manufacturer")
    private ManufacturerEntity manufacturer;

    protected BeerEntity() {
    }

    public BeerEntity(String name, Float graduation, String type, String description, ManufacturerEntity manufacturer) {
        this.name = name;
        this.graduation = graduation;
        this.type = type;
        this.description = description;
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public Float getGraduation() {
        return graduation;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public ManufacturerEntity getManufacturer() {
        return manufacturer;
    }
}
