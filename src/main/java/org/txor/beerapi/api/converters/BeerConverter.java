package org.txor.beerapi.api.converters;

import org.txor.beerapi.api.dto.BeerDTO;
import org.txor.beerapi.domain.model.Beer;

public class BeerConverter {

    private final ManufacturerConverter manufacturerConverter;

    public BeerConverter(ManufacturerConverter manufacturerConverter) {
        this.manufacturerConverter = manufacturerConverter;
    }

    public BeerDTO convert(Beer beer) {
        return new BeerDTO(beer.getName(), beer.getGraduation(), beer.getType(), beer.getDescription(), manufacturerConverter.convert(beer.getManufacturer()));
    }
}
