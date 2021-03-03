package org.txor.beerapi.repository;

import org.springframework.stereotype.Component;
import org.txor.beerapi.repository.converters.BeerEntityConverter;

@Component
public class BeerDatabaseRepository {

    private final BeerDAO repository;
    private final BeerEntityConverter converter;

    public BeerDatabaseRepository(BeerDAO repository, BeerEntityConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }
}
