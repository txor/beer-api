package org.txor.beerapi.domain.converters;

import org.txor.beerapi.domain.dto.ManufacturerDTO;
import org.txor.beerapi.domain.model.Manufacturer;

public class Manufacturer2ManufacturerDtoConverter {

    public ManufacturerDTO convert(Manufacturer manufacturer) {
        return new ManufacturerDTO(manufacturer.getName(), manufacturer.getNationality());
    }

    public Manufacturer convert(ManufacturerDTO manufacturer) {
        return null;
    }
}
