package org.txor.beerapi.domain;

import org.txor.beerapi.domain.model.Beer;

import java.util.List;

public interface BeerRepository {

    List<String> getAllBeerNames();

    void saveBeer(Beer beer);
}
