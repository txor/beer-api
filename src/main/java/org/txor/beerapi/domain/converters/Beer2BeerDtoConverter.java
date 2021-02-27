package org.txor.beerapi.domain.converters;

import org.txor.beerapi.domain.dto.BeerDTO;
import org.txor.beerapi.domain.model.Beer;

public class Beer2BeerDtoConverter {

    private final Manufacturer2ManufacturerDtoConverter manufacturerConverter;

    public Beer2BeerDtoConverter(Manufacturer2ManufacturerDtoConverter manufacturerConverter) {
        this.manufacturerConverter = manufacturerConverter;
    }

    public BeerDTO convert(Beer beer) {
        return new BeerDTO(beer.getName(), beer.getGraduation(), beer.getType(), beer.getDescription(), manufacturerConverter.convert(beer.getManufacturer()));
    }
}
