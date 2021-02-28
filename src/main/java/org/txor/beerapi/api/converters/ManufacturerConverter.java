package org.txor.beerapi.api.converters;

import org.txor.beerapi.api.dto.ManufacturerDTO;
import org.txor.beerapi.domain.model.Manufacturer;

public class ManufacturerConverter {

    public ManufacturerDTO convert(Manufacturer manufacturer) {
        return new ManufacturerDTO(manufacturer.getName(), manufacturer.getNationality());
    }

    public Manufacturer convert(ManufacturerDTO manufacturer) {
        return new Manufacturer(manufacturer.getName(), manufacturer.getNationality());
    }
}
