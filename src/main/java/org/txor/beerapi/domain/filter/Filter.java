package org.txor.beerapi.domain.filter;

import org.txor.beerapi.domain.model.Beer;

public interface Filter {
    boolean filter(Beer beer);
}
