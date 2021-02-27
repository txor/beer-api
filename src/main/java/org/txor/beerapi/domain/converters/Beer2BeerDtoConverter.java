package org.txor.beerapi.domain.converters;

import org.txor.beerapi.domain.dto.BeerDTO;
import org.txor.beerapi.domain.model.Beer;

public class Beer2BeerDtoConverter {

    private final Manufacturer2ManufacturerDtoConverter manufcaturerConverter;

    public Beer2BeerDtoConverter(Manufacturer2ManufacturerDtoConverter manufcaturerConverter) {
        this.manufcaturerConverter = manufcaturerConverter;
    }

    public BeerDTO convert(Beer beer) {
        return new BeerDTO(beer.getName(), beer.getGraduation(), beer.getType(), beer.getDescription(), manufcaturerConverter.convert(beer.getManufacturer()));
    }
}
