package org.txor.beerapi.repository.converters;

import org.springframework.stereotype.Component;
import org.txor.beerapi.domain.model.Manufacturer;
import org.txor.beerapi.repository.entity.ManufacturerEntity;

@Component
public class ManufacturerEntityConverter {

    public ManufacturerEntity convert(Manufacturer manufacturer) {
        return new ManufacturerEntity(manufacturer.getName(), manufacturer.getNationality());
    }
}
