package org.txor.beerapi.api.converters;

import org.txor.beerapi.api.dto.BeerDTO;
import org.txor.beerapi.domain.model.Beer;

public class BeerConverter {

    public BeerDTO convert(Beer beer) {
        return new BeerDTO(beer.getName(), beer.getGraduation(), beer.getType(), beer.getDescription(), beer.getManufacturer());
    }

    public Beer convert(BeerDTO beer) {
        return new Beer(beer.getName(), beer.getGraduation(), beer.getType(), beer.getDescription(), beer.getManufacturer());
    }
}
