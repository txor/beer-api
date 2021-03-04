package org.txor.beerapi.repository.converters;

import org.springframework.stereotype.Component;
import org.txor.beerapi.domain.model.Beer;
import org.txor.beerapi.repository.entity.BeerEntity;
import org.txor.beerapi.repository.entity.ManufacturerEntity;

@Component
public class BeerEntityConverter {
    public BeerEntity convert(Beer beer) {
        return new BeerEntity(beer.getName(), beer.getGraduation(), beer.getType(), beer.getDescription(), new ManufacturerEntity(beer.getManufacturer(), null));
    }

    public Beer convert(BeerEntity beer) {
        return new Beer(beer.getName(), beer.getGraduation(), beer.getType(), beer.getDescription(), beer.getManufacturer().getName());
    }
}
