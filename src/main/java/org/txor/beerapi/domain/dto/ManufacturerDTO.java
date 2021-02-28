package org.txor.beerapi.domain.dto;

import javax.validation.constraints.NotBlank;

public class ManufacturerDTO {

    @NotBlank()
    private final String name;
    private final String nationality;

    public ManufacturerDTO(String name, String nationality) {
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
