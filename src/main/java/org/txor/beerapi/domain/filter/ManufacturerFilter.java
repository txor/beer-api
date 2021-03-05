package org.txor.beerapi.domain.filter;

import org.txor.beerapi.domain.model.Beer;

public class ManufacturerFilter implements Filter {

    private final String manufacturer;

    public ManufacturerFilter(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public boolean filter(Beer beer) {
        return manufacturer.equalsIgnoreCase(beer.getManufacturer());
    }
}
