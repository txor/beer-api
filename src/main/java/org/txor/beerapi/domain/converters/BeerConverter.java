package org.txor.beerapi.domain.converters;

import org.txor.beerapi.domain.dto.BeerDTO;
import org.txor.beerapi.domain.model.Beer;

public class BeerConverter {
    public BeerDTO convert(Beer beer) {
        return new BeerDTO("", 0, "", "", null);
    }
}
