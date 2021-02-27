package org.txor.beerapi.domain.dto;

public class BeerDTO {

    private final String name;
    private final float graduation;
    private final String type;
    private final String description;
    private final ManufacturerDTO manufacturer;

    public BeerDTO(String name, float graduation, String type, String description, ManufacturerDTO manufacturer) {
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

    public ManufacturerDTO getManufacturer() {
        return manufacturer;
    }
}
