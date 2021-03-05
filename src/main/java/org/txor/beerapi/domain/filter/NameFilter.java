package org.txor.beerapi.domain.filter;

import org.txor.beerapi.domain.model.Beer;

public class NameFilter implements Filter {

    private final String name;

    public NameFilter(String name) {
        this.name = name;
    }

    @Override
    public boolean filter(Beer beer) {
        return name.equalsIgnoreCase(beer.getName());
    }
}
