package org.txor.beerapi.domain.model;

public class Beer {

    private final String name;
    private final float graduation;
    private final String type;
    private final String description;
    private final String manufacturer;

    public Beer(String name, float graduation, String type, String description, String manufacturer) {
        this.name = name;
        this.graduation = graduation;
        this.type = type;
        this.description = description;
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public float getGraduation() {
        return graduation;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getManufacturer() {
        return manufacturer;
    }
}
