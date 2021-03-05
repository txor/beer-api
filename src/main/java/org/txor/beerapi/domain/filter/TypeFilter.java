package org.txor.beerapi.domain.filter;

import org.txor.beerapi.domain.model.Beer;

public class TypeFilter implements Filter {

    private final String type;

    public TypeFilter(String type) {
        this.type = type;
    }

    @Override
    public boolean filter(Beer beer) {
        return type.equalsIgnoreCase(beer.getType());
    }
}
